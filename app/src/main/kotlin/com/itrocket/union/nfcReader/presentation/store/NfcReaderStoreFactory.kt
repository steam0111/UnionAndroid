package com.itrocket.union.nfcReader.presentation.store

import android.content.Intent
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.nfcReader.domain.NfcReaderInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.nfc.NfcManager
import com.itrocket.union.R
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documentCreate.domain.DocumentAccountingObjectManager
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documentCreate.domain.DocumentReservesManager
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.intentHandler.IntentHandler
import com.itrocket.union.nfcReader.domain.entity.NfcReaderState
import com.itrocket.union.nfcReader.domain.entity.NfcReaderType
import com.itrocket.union.transit.domain.TransitAccountingObjectManager
import com.itrocket.union.transit.domain.TransitInteractor
import com.itrocket.union.transit.domain.TransitRemainsManager
import com.itrocket.union.transit.domain.TransitTypeDomain
import com.itrocket.union.transit.presentation.store.TransitStore
import com.itrocket.union.transit.presentation.store.TransitStoreFactory
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.flow.collect

class NfcReaderStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val nfcReaderInteractor: NfcReaderInteractor,
    private val nfcReaderArguments: NfcReaderArguments,
    private val authMainInteractor: AuthMainInteractor,
    private val employeeDetailInteractor: EmployeeDetailInteractor,
    private val nfcManager: NfcManager,
    private val documentAccountingObjectManager: DocumentAccountingObjectManager,
    private val documentReservesManager: DocumentReservesManager,
    private val documentCreateInteractor: DocumentCreateInteractor,
    private val transitAccountingObjectManager: TransitAccountingObjectManager,
    private val transitReservesManager: TransitRemainsManager,
    private val transitInteractor: TransitInteractor,
    private val intentHandler: IntentHandler,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): NfcReaderStore =
        object : NfcReaderStore,
            Store<NfcReaderStore.Intent, NfcReaderStore.State, NfcReaderStore.Label> by storeFactory.create(
                name = "NfcReaderStore",
                initialState = NfcReaderStore.State(nfcReaderType = nfcReaderArguments.nfcReaderType),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NfcReaderStore.Intent, Unit, NfcReaderStore.State, Result, NfcReaderStore.Label> =
        NfcReaderExecutor()

    private inner class NfcReaderExecutor :
        BaseExecutor<NfcReaderStore.Intent, Unit, NfcReaderStore.State, Result, NfcReaderStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NfcReaderStore.State
        ) {
            intentHandler.getIntentFlow().collect {
                onNewIntent(it)
            }
        }

        override suspend fun executeIntent(
            intent: NfcReaderStore.Intent,
            getState: () -> NfcReaderStore.State
        ) {
            when (intent) {
                NfcReaderStore.Intent.OnCancelClicked -> goBack(
                    nfcReaderState = getState().nfcReaderState,
                    documentId = getState().documentId
                )
                NfcReaderStore.Intent.OnOkClicked -> goBack(
                    nfcReaderState = getState().nfcReaderState,
                    documentId = getState().documentId
                )
            }
        }

        private fun goBack(nfcReaderState: NfcReaderState, documentId: String?) {
            publish(
                NfcReaderStore.Label.GoBack(
                    NfcReaderResult(
                        isNfcReadingSuccess = nfcReaderState == NfcReaderState.Success,
                        documentId = documentId
                    )
                )
            )
        }

        private suspend fun conductDocument() {
            val type = nfcReaderArguments.nfcReaderType as NfcReaderType.DocumentConduct
            val document = type.document
            val documentId = documentCreateInteractor.createOrUpdateDocument(
                document = document,
                accountingObjects = document.accountingObjects,
                params = document.params,
                reserves = document.reserves,
                status = DocumentStatus.COMPLETED
            )
            documentAccountingObjectManager.changeAccountingObjectsAfterConduct(
                documentTypeDomain = document.documentType,
                accountingObjects = document.accountingObjects,
                params = document.params
            )
            documentReservesManager.changeReservesAfterConduct(
                documentTypeDomain = document.documentType,
                reserves = document.reserves,
                params = document.params
            )
            dispatch(Result.DocumentId(documentId))
        }

        private suspend fun conductTransit() {
            val type = nfcReaderArguments.nfcReaderType as NfcReaderType.TransitConduct
            val transit = type.transit
            val transitId = transitInteractor.createOrUpdateDocument(
                transit = transit,
                accountingObjects = transit.accountingObjects,
                params = transit.params,
                reserves = transit.reserves,
                status = DocumentStatus.COMPLETED,
                transitTypeDomain = transit.transitType ?: TransitTypeDomain.TRANSIT_SENDING
            )
            transitAccountingObjectManager.changeAccountingObjectsAfterConduct(
                transitTypeDomain = transit.transitType ?: TransitTypeDomain.TRANSIT_SENDING,
                accountingObjects = transit.accountingObjects,
                params = transit.params
            )
            transitReservesManager.conductReserve(
                reserves = transit.reserves,
                params = transit.params,
                transitTypeDomain = transit.transitType ?: TransitTypeDomain.TRANSIT_SENDING
            )
            dispatch(Result.DocumentId(transitId))
            if (transit.transitType != TransitTypeDomain.TRANSIT_RECEPTION) {
                createReceptionTransit(transitId)
            }
        }

        private suspend fun createReceptionTransit(transitId: String) {
            val transit = transitInteractor.getTransitById(transitId)
            transitInteractor.createOrUpdateDocument(
                transitTypeDomain = TransitTypeDomain.TRANSIT_RECEPTION,
                accountingObjects = transit.accountingObjects,
                reserves = transit.reserves,
                params = transit.params,
                status = DocumentStatus.CREATED,
                transit = transit.copy(id = null)
            )
        }

        private suspend fun onNewIntent(intent: Intent) {
            catchException {
                val isSuccess = nfcReaderInteractor.onNewIntent(intent)
                if (isSuccess) {
                    dispatch(Result.Loading(true))
                    when (nfcReaderArguments.nfcReaderType) {
                        is NfcReaderType.DocumentConduct -> conductDocument()
                        is NfcReaderType.TransitConduct -> conductTransit()
                    }
                    dispatch(Result.State(NfcReaderState.Success))
                    dispatch(Result.Loading(false))
                    nfcManager.disableForegroundNfcDispatch()
                } else {
                    dispatch(Result.State(NfcReaderState.Error(R.string.nfc_reader_error)))
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(NfcReaderStore.Label.Error(errorInteractor.getTextMessage(throwable)))
            dispatch(Result.Loading(false))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class State(val nfcReaderState: NfcReaderState) : Result()
        data class DocumentId(val documentId: String) : Result()
    }

    private object ReducerImpl : Reducer<NfcReaderStore.State, Result> {
        override fun NfcReaderStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.State -> copy(nfcReaderState = result.nfcReaderState)
                is Result.DocumentId -> copy(documentId = result.documentId)
            }
    }
}
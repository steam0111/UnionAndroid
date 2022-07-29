package com.itrocket.union.identify.presentation.store

import android.util.Log
import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.ReservesInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.coroutines.delay

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: IdentifyInteractor,
    private val identifyArguments: IdentifyArguments?,
    private val accountingObjectInteractor: AccountingObjectInteractor,
    private val reservesInteractor: ReservesInteractor,
) {
    lateinit var itemDomain: AccountingObjectDomain
    fun create(): IdentifyStore =
        object : IdentifyStore,
            Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> by storeFactory.create(
                name = "IdentifyStore",
                initialState = IdentifyStore.State(
                    os = initOs()
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun initOs(): List<AccountingObjectDomain> {
        return identifyArguments?.accountingObjectDomain ?: listOf()
    }

    private fun createExecutor(): Executor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label> =
        IdentifyExecutor()

    private inner class IdentifyExecutor :
        BaseExecutor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label>(
            context = coreDispatchers.ui
        ) {

        override suspend fun executeAction(
            action: Unit,
            getState: () -> IdentifyStore.State
        ) {
            dispatch(Result.Loading(true))
            delay(1000)
            observeAccountingObjects()
//            dispatch(Result.Identify(identifyInteractor.getAccountingObjects()))
            catchException {
                val docArgument = identifyArguments?.document
                val listAfterDel = identifyArguments?.accountingObjectDomain
                val document = if (docArgument?.isDocumentExists == true) {
                    docArgument.id.let { identifyInteractor.getDocumentById(it) }
                } else {
                    null
                }

                document?.let { dispatch(Result.Document(it)) }
                dispatch(
                    Result.AccountingObjects(listAfterDel ?: listOf())
//                    Result.AccountingObjects(document?.accountingObjects ?: listOf())
                )
                dispatch(Result.Reserves(document?.reserves ?: listOf()))
            }

            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: IdentifyStore.Intent,
            getState: () -> IdentifyStore.State
        ) {
            when (intent) {
//Toolbar
                IdentifyStore.Intent.OnBackClicked -> publish(IdentifyStore.Label.GoBack)

                IdentifyStore.Intent.OnDropClicked -> {
                    dispatch(Result.AccountingObjects(listOf()))
                }

                IdentifyStore.Intent.OnReadingModeClicked -> {
                    publish(IdentifyStore.Label.ShowReadingMode)
                }

//Главное окно
                is IdentifyStore.Intent.OnItemClicked -> {
                    itemDomain = intent.item
                    publish(
                        IdentifyStore.Label.ShowDetail(
                            intent.item,
                            getState().os
                        )
                    )
                    Log.d("SukhanovTest", "Click Item " + intent.item.title)
                }

                is IdentifyStore.Intent.OnObjectActionSelected ->
                    when (intent.objectAction) {
                        ObjectAction.CREATE_DOC -> {
                            Log.d("SukhanovTest", "Click CREATE")

                        }
                        ObjectAction.DELETE_FROM_LIST -> {
                            Log.d("SukhanovTest", "Click DELETE")
                        }
                        ObjectAction.OPEN_CARD -> {
                            Log.d("SukhanovTest", "Click OPEN $itemDomain")
                            publish(IdentifyStore.Label.OpenCardOS(itemDomain))
                        }
                    }
                is IdentifyStore.Intent.OnNewAccountingObjectRfidsHandled -> handleRfidsAccountingObjects(
                    intent.rfids,
                    getState().os
                )
                is IdentifyStore.Intent.OnNewAccountingObjectBarcodeHandled -> {
                    handleBarcodeAccountingObjects(
                        intent.barcode,
                        getState().os
                    )
                }
                is IdentifyStore.Intent.OnAccountingObjectSelected -> {
                    dispatch(
                        Result.AccountingObjects(
                            identifyInteractor.addAccountingObject(
                                accountingObjects = getState().os,
                                accountingObjectDomain = intent.accountingObjectDomain
                            )
                        )
                    )
                }
                is IdentifyStore.Intent.OnDeleteFromBottomAction -> {
                    dispatch(Result.AccountingObjects(intent.bottomActionResult))
                }
            }
        }

        private suspend fun observeAccountingObjects(params: List<ParamDomain> = listOf()) {
            dispatch(Result.IsAccountingObjectsLoading(true))
            catchException {
                dispatch(Result.IsAccountingObjectsLoading(false))
                dispatch(
                    Result.AccountingObjects(
                        accountingObjectInteractor.getAccountingObjects("", params)
                    )
                )
                dispatch(Result.IsAccountingObjectsLoading(false))
            }
        }

        private suspend fun observeReserves(params: List<ParamDomain> = listOf()) {
            dispatch(Result.IsReservesLoading(true))
            catchException {
                dispatch(Result.IsReservesLoading(false))
                dispatch(
                    Result.Reserves(
                        reservesInteractor.getReserves("", params)
                    )
                )
                dispatch(Result.IsReservesLoading(false))
            }
        }


        private suspend fun handleRfidsAccountingObjects(
            rfids: List<String>,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = identifyInteractor.handleNewAccountingObjectRfids(
                accountingObjects = accountingObjects,
                handledAccountingObjectRfids = rfids
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }

        private suspend fun handleBarcodeAccountingObjects(
            barcode: String,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            val newAccountingObjects = identifyInteractor.handleNewAccountingObjectBarcode(
                accountingObjects = accountingObjects,
                barcode = barcode
            )
            dispatch(Result.AccountingObjects(newAccountingObjects))
        }
    }

    private sealed class Result {
        data class Document(val document: IdentifyDomain) : Result()
        data class IsAccountingObjectsLoading(val isLoading: Boolean) :
            IdentifyStoreFactory.Result()

        data class IsReservesLoading(val isLoading: Boolean) :
            IdentifyStoreFactory.Result()

        data class Loading(val isLoading: Boolean) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) :
            Result()

        data class IsBottomActionMenuLoading(val isLoading: Boolean) : IdentifyStoreFactory.Result()

    }

    private object ReducerImpl : Reducer<IdentifyStore.State, Result> {
        override fun IdentifyStore.State.reduce(result: Result): IdentifyStore.State =
            when (result) {
                is Result.Loading -> copy(isIdentifyLoading = result.isLoading)
                is Result.AccountingObjects -> copy(os = result.accountingObjects)
                is Result.Reserves -> copy(reserves = result.reserves)
                is Result.IsBottomActionMenuLoading -> copy(isBottomActionMenuLoading = result.isLoading)

                else -> copy(isIdentifyLoading = true)
            }
    }
}
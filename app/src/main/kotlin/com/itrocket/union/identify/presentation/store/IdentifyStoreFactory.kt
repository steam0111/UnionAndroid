package com.itrocket.union.identify.presentation.store

import android.util.Log
import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.ObjectAction
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.identify.domain.entity.OSandReserves
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.coroutines.delay

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: IdentifyInteractor,
    private val filterInteractor: FilterInteractor,
    private val accountingObjectInteractor: AccountingObjectInteractor,


//    private val accountingObjectDetailArguments: AccountingObjectDetailArguments

) {
    lateinit var itemDomain: OSandReserves
    fun create(): IdentifyStore =
        object : IdentifyStore,
            Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> by storeFactory.create(
                name = "IdentifyStore",
                initialState = IdentifyStore.State(),
//                    accountingObjectDomain = accountingObjectDetailArguments.argument

                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

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
//            dispatch(Result.Identify(identifyInteractor.getAccountingObjects()))
            dispatch(Result.Loading(false))
            observeAccountingObjects()
        }

        override suspend fun executeIntent(
            intent: IdentifyStore.Intent,
            getState: () -> IdentifyStore.State
        ) {
            when (intent) {
                IdentifyStore.Intent.OnBackClicked -> publish(IdentifyStore.Label.GoBack)
                IdentifyStore.Intent.OnSaveClicked -> {
                    publish(IdentifyStore.Label.ShowSave)
                    Log.d("SukhanovTest", "Click Identify Save Button")
                }
                IdentifyStore.Intent.OnFilterClicked -> publish(
                    IdentifyStore.Label.ShowFilter(filterInteractor.getFilters())
                )
                is IdentifyStore.Intent.OnOSClicked -> {
                    publish(IdentifyStore.Label.ShowReadingMode(getState().readingMode))
//                    publish(IdentifyStore.Label.ShowIdentifyItem(getState().bottomActionMenuTab))


                    Log.d("SukhanovTest", "Click Identify Item Button" + intent.item.title)
                }

                IdentifyStore.Intent.OnDropClicked -> {
                    Log.d("SukhanovTest", "Click Identify Drop Button")
                }
                is IdentifyStore.Intent.OnSelectPage -> dispatch((Result.SelectPage(intent.selectedPage)))

                IdentifyStore.Intent.OnReadingModeClicked -> {
                    Log.d("SukhanovTest", "Click ShowReadingMode Button")
                    publish(IdentifyStore.Label.ShowReadingMode(getState().readingMode))
                }
//                is IdentifyStore.Intent.OnItemClicked -> publish(
//                    IdentifyStore.Label.ShowDetail(intent.item)
//                )
                is IdentifyStore.Intent.OnReservesClicked -> {
                    itemDomain = intent.item
                    publish(
                        IdentifyStore.Label.ShowDetail(intent.item)
                    )
                }

//                    publish(                    IdentifyStore.Label.ShowDetail(intent.item)

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
                            when (itemDomain) {
                                is AccountingObjectDomain -> {
                                    publish(IdentifyStore.Label.OpenCardOS(itemDomain))

                                }
                                is ReservesDomain -> {
                                    publish(IdentifyStore.Label.OpenCardReserves(itemDomain))
                                }
                            }

                        }
                    }
//                is IdentifyStore.Intent.OnObjectActionSelected ->
//                    initAction(listType = intent.objectAction)
//                is IdentifyStore.Intent.OnOpenCard -> {
//                    publish(IdentifyStore.Label.OpenCard(intent.item))

            }
        }


//        private suspend fun initAction(listType: ObjectAction) {
//            dispatch(Result.IsBottomActionMenuLoading(true))
//            when (listType) {
//                ObjectAction.CREATE_DOC -> {
//                    Log.d("SukhanovTest", "Click CREATE")
//
//                }
//                ObjectAction.DELETE_FROM_LIST -> {
//                    Log.d("SukhanovTest", "Click DELETE")
//                }
//                ObjectAction.OPEN_CARD -> {
//                    Log.d("SukhanovTest", "Click OPEN")
//                    accept(IdentifyStore.Intent.OnReservesClicked(it))
//                }
//            }
////            val document = identifyInteractor.createDocument(listType)
////            showDocument(document)
//            dispatch(Result.IsBottomActionMenuLoading(false))
//        }

//        private fun showDocument(document: IdentifyDomain) {
//            publish(
//                IdentifyStore.Label.ShowDocumentCreate(
//                    document = document
//                )
//            )
//        }

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
    }

    private sealed class Result {
        data class IsAccountingObjectsLoading(val isLoading: Boolean) :
            IdentifyStoreFactory.Result()

        data class Loading(val isLoading: Boolean) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) :
            Result()

        data class SelectPage(val page: Int) : IdentifyStoreFactory.Result()
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
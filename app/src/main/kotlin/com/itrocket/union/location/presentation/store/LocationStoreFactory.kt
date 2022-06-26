package com.itrocket.union.location.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.location.domain.LocationInteractor
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.delay

class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val locationInteractor: LocationInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): LocationStore =
        object : LocationStore,
            Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> by storeFactory.create(
                name = "LocationStore",
                initialState = LocationStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<LocationStore.Intent, Unit, LocationStore.State, Result, LocationStore.Label> =
        LocationExecutor()

    private inner class LocationExecutor :
        BaseExecutor<LocationStore.Intent, Unit, LocationStore.State, Result, LocationStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> LocationStore.State
        ) {
            searchManager.listenSearch {
                search(getState, it)
            }
            dispatch(Result.Loading(true))
            catchException {
                val placeList = locationInteractor.getPlaceList(listOf())
                dispatch(Result.PlaceValues(placeList))
                dispatch(
                    Result.LevelHint(
                        placeList.firstOrNull()?.type.orEmpty()
                    )
                )
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: LocationStore.Intent,
            getState: () -> LocationStore.State
        ) {
            when (intent) {
                LocationStore.Intent.OnBackClicked -> {
                    onBackClicked(getState)
                }
                LocationStore.Intent.OnAcceptClicked -> goBack(getState)
                LocationStore.Intent.OnCrossClicked -> publish(LocationStore.Label.GoBack())
                LocationStore.Intent.OnFinishClicked -> goBack(getState)
                is LocationStore.Intent.OnPlaceSelected -> {
                    selectPlace(getState, intent.place)
                }
                is LocationStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
            }
        }

        private suspend fun search(getState: () -> LocationStore.State, searchText: String) {
            val selectedPlaceScheme = getState().selectPlaceScheme
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.PlaceValues(
                        locationInteractor.getPlaceList(selectedPlaceScheme, searchText)
                    )
                )
            }
            dispatch(Result.Loading(false))
        }

        private fun goBack(getState: () -> LocationStore.State) {
            publish(
                LocationStore.Label.GoBack(
                    LocationResult(
                        LocationParamDomain(
                            ids = getState().selectPlaceScheme.map { it.id },
                            values = getState().selectPlaceScheme.map { it.value }
                        )
                    )
                )
            )
        }

        private suspend fun selectPlace(
            getState: () -> LocationStore.State,
            place: LocationDomain
        ) {
            val placeValues = getState().placeValues
            val selectedPlaceScheme = getState().selectPlaceScheme
            dispatch(
                Result.SelectPlaceScheme(
                    locationInteractor.resolveNewPlace(
                        selectedPlaceScheme = selectedPlaceScheme,
                        selectedPlace = place,
                        isRemoveLast = placeValues.contains(selectedPlaceScheme.lastOrNull())
                    )
                )
            )
            dispatch(Result.LevelHint(""))
            delay(300) //Задержка для того чтобы успела отработать анимация радио баттона выбора местоположения

            dispatch(Result.Loading(true))
            catchException {
                val newPlaceValues =
                    locationInteractor.getPlaceList(getState().selectPlaceScheme)
                if (locationInteractor.isNewPlaceList(
                        newList = newPlaceValues,
                        oldList = placeValues
                    )
                ) {
                    dispatch(Result.PlaceValues(newPlaceValues))
                    dispatch(
                        Result.LevelHint(
                            newPlaceValues.firstOrNull()?.type.orEmpty()
                        )
                    )
                }
            }
            dispatch(Result.SearchText(""))
            dispatch(Result.Loading(false))
        }

        private suspend fun onBackClicked(getState: () -> LocationStore.State) {
            val selectedPlaceScheme = getState().selectPlaceScheme
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.SelectPlaceScheme(
                        locationInteractor.removeLastPlace(
                            selectedPlaceScheme = selectedPlaceScheme
                        )
                    )
                )
                val placeList = locationInteractor.getPlaceList(
                    locationInteractor.getPrevPlaceScheme(
                        getState().selectPlaceScheme
                    )
                )
                dispatch(Result.PlaceValues(placeList))
                dispatch(
                    Result.LevelHint(
                        placeList.firstOrNull()?.type.orEmpty()
                    )
                )
            }
            dispatch(Result.SearchText(""))
            dispatch(Result.Loading(false))
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(LocationStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class SelectPlaceScheme(val selectPlaceScheme: List<LocationDomain>) : Result()
        data class PlaceValues(val placeValues: List<LocationDomain>) : Result()
        data class LevelHint(val levelHint: String) : Result()
        data class SearchText(val searchText: String) : Result()
    }

    private object ReducerImpl : Reducer<LocationStore.State, Result> {
        override fun LocationStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.SelectPlaceScheme -> copy(selectPlaceScheme = result.selectPlaceScheme)
                is Result.PlaceValues -> copy(placeValues = result.placeValues)
                is Result.LevelHint -> copy(levelHint = result.levelHint)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}
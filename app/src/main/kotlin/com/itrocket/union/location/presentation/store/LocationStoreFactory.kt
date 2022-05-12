package com.itrocket.union.location.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.location.domain.LocationInteractor
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.delay

class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val locationInteractor: LocationInteractor
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
        SuspendExecutor<LocationStore.Intent, Unit, LocationStore.State, Result, LocationStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> LocationStore.State
        ) {
            dispatch(Result.Loading(true))
            val placeList = locationInteractor.getPlaceList(listOf())
            dispatch(Result.PlaceValues(placeList))
            dispatch(
                Result.LevelHint(
                    placeList.firstOrNull()?.type.orEmpty()
                )
            )
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: LocationStore.Intent,
            getState: () -> LocationStore.State
        ) {
            when (intent) {
                LocationStore.Intent.OnBackClicked -> {
                    val selectedPlaceScheme = getState().selectPlaceScheme
                    dispatch(Result.Loading(true))
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
                    dispatch(Result.SearchText(""))
                    dispatch(Result.Loading(false))
                }
                LocationStore.Intent.OnAcceptClicked -> publish(
                    LocationStore.Label.GoBack(
                        LocationResult(
                            locationInteractor.placeSchemaToString(getState().selectPlaceScheme)
                        )
                    )
                )
                LocationStore.Intent.OnCrossClicked -> publish(LocationStore.Label.GoBack())
                LocationStore.Intent.OnFinishClicked -> publish(
                    LocationStore.Label.GoBack(
                        LocationResult(
                            locationInteractor.placeSchemaToString(getState().selectPlaceScheme)
                        )
                    )
                )
                is LocationStore.Intent.OnPlaceSelected -> {
                    val placeValues = getState().placeValues
                    val selectedPlaceScheme = getState().selectPlaceScheme
                    dispatch(
                        Result.SelectPlaceScheme(
                            locationInteractor.resolveNewPlace(
                                selectedPlaceScheme = selectedPlaceScheme,
                                selectedPlace = intent.place,
                                isRemoveLast = placeValues.contains(selectedPlaceScheme.lastOrNull())
                            )
                        )
                    )
                    dispatch(Result.LevelHint(""))
                    delay(300) //Задержка для того чтобы успела отработать анимация радио баттона выбора местоположения

                    dispatch(Result.Loading(true))
                    val newPlaceValues =
                        locationInteractor.getPlaceList(getState().selectPlaceScheme)
                    if (newPlaceValues.isNotEmpty()) {
                        dispatch(Result.PlaceValues(newPlaceValues))
                    }
                    dispatch(
                        Result.LevelHint(
                            newPlaceValues.firstOrNull()?.type.orEmpty()
                        )
                    )
                    dispatch(Result.SearchText(""))
                    dispatch(Result.Loading(false))
                }
                is LocationStore.Intent.OnSearchTextChanged -> {
                    val selectedPlaceScheme = getState().selectPlaceScheme
                    dispatch(Result.Loading(true))
                    dispatch(
                        Result.PlaceValues(
                            locationInteractor.getPlaceList(selectedPlaceScheme, intent.searchText)
                        )
                    )
                    dispatch(Result.SearchText(intent.searchText))
                    dispatch(Result.Loading(false))
                }
            }
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
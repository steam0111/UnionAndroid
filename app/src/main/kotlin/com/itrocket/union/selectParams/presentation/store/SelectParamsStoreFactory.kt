package com.itrocket.union.selectParams.presentation.store

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
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.manual.getIndexByType
import com.itrocket.union.manual.isDefaultParamType
import com.itrocket.union.search.SearchManager
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.selectParams.domain.SelectParamsInteractor.Companion.MIN_CURRENT_STEP
import com.itrocket.union.structural.domain.StructuralInteractor
import com.itrocket.union.structural.domain.entity.StructuralDomain
import kotlin.math.max
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class SelectParamsStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val selectParamsInteractor: SelectParamsInteractor,
    private val selectParamsArguments: SelectParamsArguments,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager,
    private val locationInteractor: LocationInteractor,
    private val structuralInteractor: StructuralInteractor,
) {
    fun create(): SelectParamsStore {
        val params =
            selectParamsArguments.allParams.filter { it.isClickable && it.isDefaultParamType() }
        return object : SelectParamsStore,
            Store<SelectParamsStore.Intent, SelectParamsStore.State, SelectParamsStore.Label> by storeFactory.create(
                name = "SelectParamsStore",
                initialState = SelectParamsStore.State(
                    currentStep = params.getIndexByType(
                        selectParamsArguments.currentFilter.type
                    ),
                    allParams = params,
                    currentParam = selectParamsArguments.currentFilter
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}
    }

    private fun createExecutor(): Executor<SelectParamsStore.Intent, Unit, SelectParamsStore.State, Result, SelectParamsStore.Label> =
        SelectParamsExecutor()

    private inner class SelectParamsExecutor :
        BaseExecutor<SelectParamsStore.Intent, Unit, SelectParamsStore.State, Result, SelectParamsStore.Label>(
            context = coreDispatchers.ui
        ) {

        override suspend fun executeAction(
            action: Unit,
            getState: () -> SelectParamsStore.State
        ) {
            searchManager.listenSearch { searchText ->
                dispatch(Result.Loading(true))
                doAccordingParamType(getState = getState, searchText = searchText)
                dispatch(Result.Loading(false))
            }
        }

        override suspend fun executeIntent(
            intent: SelectParamsStore.Intent,
            getState: () -> SelectParamsStore.State
        ) {
            when (intent) {
                SelectParamsStore.Intent.OnAcceptClicked -> {
                    publish(SelectParamsStore.Label.GoBack(result = SelectParamsResult(getState().allParams)))
                }
                is SelectParamsStore.Intent.OnSearchTextChanged -> {
                    onSearchTextChanged(getState, intent.searchText)
                }
                SelectParamsStore.Intent.OnCrossClicked -> {
                    publish(SelectParamsStore.Label.GoBack())
                }
                is SelectParamsStore.Intent.OnCommonItemSelected -> {
                    onCommonItemSelected(getState, intent.item)
                }
                SelectParamsStore.Intent.OnNextClicked -> {
                    onNextClicked(getState)
                }
                SelectParamsStore.Intent.OnPrevClicked -> {
                    onPrevClicked(getState)
                }
                SelectParamsStore.Intent.OnLocationBackClicked -> {
                    onLocationBackClicked(getState)
                }
                is SelectParamsStore.Intent.OnLocationSelected -> {
                    onLocationSelected(getState, intent.location)
                }
                SelectParamsStore.Intent.OnStructuralBackClicked -> {
                    onStructuralBackClicked(getState)
                }
                is SelectParamsStore.Intent.OnStructuralSelected -> {
                    onStructuralSelected(getState, intent.structural)
                }
            }
        }

        private suspend fun onStructuralSelected(
            getState: () -> SelectParamsStore.State,
            structural: StructuralDomain
        ) {
            val currentParam = getState().currentParam as StructuralParamDomain
            val structuralValuesOnScreen = getState().structuralValues
            val selectedStructurals = currentParam.structurals
            val newSelectedStructurals = structuralInteractor.resolveNewStructural(
                selectedStructuralScheme = selectedStructurals,
                selectedStructural = structural,
                isRemoveLast = structuralValuesOnScreen.contains(selectedStructurals.lastOrNull())
            )

            val structuralParamIndex =
                getState().allParams.getIndexByType(getState().currentParam.type)
            val newParam = currentParam.copy(structurals = newSelectedStructurals)
            val newParams = getState().allParams.toMutableList()
            newParams[structuralParamIndex] = newParam

            dispatch(Result.CurrentParam(newParam))
            dispatch(Result.AllParams(newParams))

            catchException {
                val newStructuralValuesOnScreen = structuralInteractor.getStructuralList(structural)
                if (structuralInteractor.isNewStructuralist(
                        newList = newStructuralValuesOnScreen,
                        oldList = structuralValuesOnScreen
                    )
                ) {
                    dispatch(Result.StructuralValues(newStructuralValuesOnScreen))
                    dispatch(Result.IsLevelHintShow(newStructuralValuesOnScreen.isNotEmpty()))
                }
            }
            dispatch(Result.SearchText(""))
        }

        private suspend fun onStructuralBackClicked(getState: () -> SelectParamsStore.State) {
            catchException {
                val currentParam = getState().currentParam as StructuralParamDomain
                val selectedStructurals = currentParam.structurals
                val newSelectedStructurals = structuralInteractor.removeLastStructurals(
                    selectedStructuralScheme = selectedStructurals
                )
                val newStructuralsOnScreen = structuralInteractor.getStructuralList(
                    structuralInteractor.removeLastStructurals(
                        selectedStructuralScheme = newSelectedStructurals
                    ).lastOrNull()
                )

                val structuralParamIndex =
                    getState().allParams.getIndexByType(getState().currentParam.type)
                val newParam = currentParam.copy(structurals = newSelectedStructurals)
                val newParams = getState().allParams.toMutableList()
                newParams[structuralParamIndex] = newParam

                dispatch(Result.AllParams(newParams))
                dispatch(Result.CurrentParam(newParam))
                dispatch(Result.StructuralValues(newStructuralsOnScreen))
                dispatch(Result.IsLevelHintShow(newStructuralsOnScreen.isNotEmpty()))
                dispatch(Result.SearchText(""))
            }
        }

        private suspend fun onLocationSelected(
            getState: () -> SelectParamsStore.State,
            location: LocationDomain
        ) {
            val currentParam = getState().currentParam as LocationParamDomain
            val locationsOnScreen = getState().locationValues
            val selectedLocations = currentParam.locations
            val newLocations = locationInteractor.resolveNewPlace(
                selectedPlaceScheme = selectedLocations,
                selectedPlace = location,
                isRemoveLast = locationsOnScreen.contains(selectedLocations.lastOrNull())
            )

            val locationParamIndex =
                getState().allParams.getIndexByType(getState().currentParam.type)
            val newParam = currentParam.copy(locations = newLocations)
            val newParams = getState().allParams.toMutableList()
            newParams[locationParamIndex] = newParam

            dispatch(Result.AllParams(newParams))
            dispatch(Result.CurrentParam(newParam))
            dispatch(Result.LevelHint(""))
            dispatch(Result.SearchText(""))

            catchException {
                val newLocationsOnScreen = locationInteractor.getPlaceList(location)
                if (locationInteractor.isNewPlaceList(
                        newList = newLocationsOnScreen,
                        oldList = locationsOnScreen
                    )
                ) {
                    dispatch(Result.LocationValues(newLocationsOnScreen))
                    dispatch(
                        Result.LevelHint(
                            newLocationsOnScreen.firstOrNull()?.type.orEmpty()
                        )
                    )
                }
            }
        }

        private suspend fun onLocationBackClicked(getState: () -> SelectParamsStore.State) {
            catchException {
                val locationParam = getState().currentParam as LocationParamDomain
                val selectedLocations = locationParam.locations
                val newSelectedLocations =
                    locationInteractor.removeLastPlace(selectedPlaceScheme = selectedLocations)
                val newLocationsOnScreen =
                    locationInteractor.getPlaceList(
                        locationInteractor.removeLastPlace(
                            selectedPlaceScheme = newSelectedLocations
                        ).lastOrNull()
                    )

                val locationParamIndex =
                    getState().allParams.getIndexByType(getState().currentParam.type)
                val newParam = locationParam.copy(locations = newSelectedLocations)
                val newParams = getState().allParams.toMutableList()
                newParams[locationParamIndex] = newParam

                dispatch(Result.CurrentParam(newParam))
                dispatch(Result.AllParams(newParams))
                dispatch(Result.LocationValues(newLocationsOnScreen))
                dispatch(Result.LevelHint(newLocationsOnScreen.firstOrNull()?.type.orEmpty()))
                dispatch(Result.SearchText(""))
            }
        }

        private suspend fun onSearchTextChanged(
            getState: () -> SelectParamsStore.State,
            searchText: String
        ) {
            if (searchText != getState().searchText) {
                dispatch(Result.SearchText(searchText))
                searchManager.emit(searchText)
            }
        }

        private fun onCommonItemSelected(
            getState: () -> SelectParamsStore.State,
            item: ParamDomain
        ) {
            val newParamsValues = selectParamsInteractor.changeParamValue(
                params = getState().allParams,
                currentStep = getState().currentStep,
                paramValue = item
            )
            dispatch(Result.AllParams(newParamsValues))
            dispatch(Result.CurrentParam(getState().allParams[getState().currentStep]))
        }

        private suspend fun onPrevClicked(getState: () -> SelectParamsStore.State) {
            dispatch(Result.Step(max(getState().currentStep - 1, MIN_CURRENT_STEP)))

            val currentParam = getState().allParams[getState().currentStep]
            dispatch(Result.CurrentParam(currentParam))
            dispatch(Result.SearchText(currentParam.value))
            doAccordingParamType(getState)
        }

        private suspend fun onNextClicked(getState: () -> SelectParamsStore.State) {
            if (getState().currentStep == getState().allParams.lastIndex) {
                publish(
                    SelectParamsStore.Label.GoBack(
                        result = SelectParamsResult(getState().allParams)
                    )
                )
            } else {
                dispatch(Result.Loading(true))
                dispatch(Result.Step(getState().currentStep + 1))

                val currentParam = getState().allParams[getState().currentStep]
                dispatch(Result.CurrentParam(currentParam))
                dispatch(Result.SearchText(currentParam.value))
                doAccordingParamType(getState)
                dispatch(Result.Loading(false))
            }
        }

        private suspend fun doAccordingParamType(
            getState: () -> SelectParamsStore.State,
            searchText: String? = null
        ) {
            when (val currentParam = getState().currentParam) {
                is StructuralParamDomain -> {
                    workWithStructural(
                        param = currentParam,
                        searchText = searchText ?: currentParam.value
                    )
                }
                is LocationParamDomain -> {
                    workWithLocation(
                        param = currentParam,
                        searchText = searchText ?: currentParam.value
                    )
                }
                else -> {
                    workWithCommon(
                        allParams = getState().allParams,
                        type = currentParam.type,
                        searchText = searchText ?: currentParam.value
                    )
                }
            }
        }

        private suspend fun workWithLocation(
            param: LocationParamDomain,
            searchText: String
        ) {
            catchException {
                var locationsOnScreen = locationInteractor.getPlaceList(
                    param.locations.lastOrNull(),
                    searchText
                )
                if (locationsOnScreen.isEmpty()) {
                    val index = max(param.locations.lastIndex - 1, 0)
                    locationsOnScreen = locationInteractor.getPlaceList(
                        param.locations[index],
                        searchText
                    )
                }
                dispatch(Result.LocationValues(locationsOnScreen))
                dispatch(Result.LevelHint(locationsOnScreen.firstOrNull()?.type.orEmpty()))
            }
        }

        private suspend fun workWithStructural(
            param: StructuralParamDomain,
            searchText: String = ""
        ) {
            catchException {
                var structuralList = structuralInteractor.getStructuralList(
                    param.structurals.lastOrNull(),
                    searchText
                )

                if (structuralList.isEmpty() && param.structurals.isNotEmpty()) {
                    val index = max(param.structurals.lastIndex - 1, 0)
                    structuralList = structuralInteractor.getStructuralList(
                        param.structurals[index],
                        searchText
                    )
                }

                dispatch(Result.StructuralValues(structuralList))
                dispatch(Result.IsLevelHintShow(structuralList.isNotEmpty()))
            }
        }

        private suspend fun workWithCommon(
            allParams: List<ParamDomain>,
            type: ManualType,
            searchText: String
        ) {
            catchException {
                selectParamsInteractor.getParamValues(
                    allParams = allParams,
                    type = type,
                    searchText = searchText,
                    sourceScreen = selectParamsArguments.sourceScreen
                )
                    .catch { handleError(it) }
                    .collect {
                        dispatch(Result.CommonValues(it))
                    }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(SelectParamsStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Step(val currentStep: Int) : Result()
        data class CurrentParam(val currentParam: ParamDomain) : Result()
        data class AllParams(val params: List<ParamDomain>) : Result()
        data class SearchText(val searchText: String) : Result()

        data class LevelHint(val levelHint: String) : Result()
        data class IsLevelHintShow(val isLevelHintShow: Boolean) : Result()

        data class StructuralValues(val structurals: List<StructuralDomain>) : Result()
        data class LocationValues(val locations: List<LocationDomain>) : Result()
        data class CommonValues(val values: List<ParamDomain>) : Result()
    }

    private object ReducerImpl : Reducer<SelectParamsStore.State, Result> {
        override fun SelectParamsStore.State.reduce(result: Result) =
            when (result) {
                is Result.Step -> copy(currentStep = result.currentStep)
                is Result.AllParams -> copy(allParams = result.params)
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.CommonValues -> copy(commonParamValues = result.values)

                is Result.LocationValues -> copy(locationValues = result.locations)
                is Result.LevelHint -> copy(levelHint = result.levelHint)
                is Result.CurrentParam -> copy(currentParam = result.currentParam)
                is Result.IsLevelHintShow -> copy(isLevelHintShow = result.isLevelHintShow)
                is Result.StructuralValues -> copy(structuralValues = result.structurals)
            }
    }
}
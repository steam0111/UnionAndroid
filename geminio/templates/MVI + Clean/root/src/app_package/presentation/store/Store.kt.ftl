package ${packageName}.${featurePackageName}.presentation.store

import com.itrocket.core.navigation.GoBackNavigationLabel
import com.arkivanov.mvikotlin.core.store.Store

interface ${featureName}Store : Store<${featureName}Store.Intent, ${featureName}Store.State, ${featureName}Store.Label> {

    sealed class Intent {
        object OnBackClicked : Intent()
    }

    data class State(
        val isLoading: Boolean = false
    )

    sealed class Label {
        object GoBack : Label(), GoBackNavigationLabel
    }
}
package ${packageName}.${featurePackageName}.presentation.view

import androidx.compose.ui.platform.ComposeView
import ${packageName}.${featurePackageName}.${featureName}Module.${featureName?upper_case}_VIEW_MODEL_QUALIFIER
import ${packageName}.${featurePackageName}.presentation.store.${featureName}Store
import ${packageName}.core.BaseComposeFragment
import ru.interid.weatherford.core.AppInsets
import androidx.navigation.fragment.navArgs
import ${packageName}.${featurePackageName}.presentation.view.${featureName}ComposeFragmentArgs

class ${featureName}ComposeFragment : BaseComposeFragment<${featureName}Store.Intent, ${featureName}Store.State, ${featureName}Store.Label>(
    ${featureName?upper_case}_VIEW_MODEL_QUALIFIER
) {
    override val navArgs by navArgs<${featureName}ComposeFragmentArgs>()

    override fun renderState(state: ${featureName}Store.State, composeView: ComposeView, appInsets: AppInsets) {
        composeView.setContent {
            ${featureName}Screen(
                state = state,
                appInsets = appInsets,
                onBackClickListener = {
                   accept(${featureName}Store.Intent.OnBackClicked)
                }
            )
        }
    }
}
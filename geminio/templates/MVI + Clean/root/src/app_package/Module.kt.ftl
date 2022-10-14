package ${packageName}.${featurePackageName}

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.core.parameter.parametersOf
import ${packageName}.${featurePackageName}.data.${featureName}RepositoryImpl
import ${packageName}.${featurePackageName}.domain.${featureName}Interactor
import ${packageName}.${featurePackageName}.domain.dependencies.${featureName}Repository
import ${packageName}.${featurePackageName}.presentation.store.${featureName}Store
import ${packageName}.${featurePackageName}.presentation.store.${featureName}StoreFactory
import ${packageName}.${featurePackageName}.presentation.view.${featureName}ComposeFragmentArgs
import com.itrocket.core.base.BaseViewModel

object ${featureName}Module {
    val ${featureName?upper_case}_VIEW_MODEL_QUALIFIER = named("${featureName?upper_case}_VIEW_MODEL")

    val module = module {
        viewModel(${featureName?upper_case}_VIEW_MODEL_QUALIFIER) { (args: ${featureName}ComposeFragmentArgs) ->
            BaseViewModel(get<${featureName}Store>() {
                parametersOf(args)
            })
        }

        factory<${featureName}Repository> {
            ${featureName}RepositoryImpl(get())
        }

        factory {
            ${featureName}Interactor(get(), get())
        }

        factory { (args: ${featureName}ComposeFragmentArgs) ->
            ${featureName}StoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args.${featureName?uncap_first}Arguments,
                get()
            ).create()
        }
    }
}
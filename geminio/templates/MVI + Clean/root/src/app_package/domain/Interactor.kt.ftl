package ${packageName}.${featurePackageName}.domain

import kotlinx.coroutines.withContext
import ${packageName}.${featurePackageName}.domain.dependencies.${featureName}Repository
import ${packageName}.core.CoreDispatchers

class ${featureName}Interactor(
    private val repository: ${featureName}Repository,
    private val coreDispatchers: CoreDispatchers
) {

}
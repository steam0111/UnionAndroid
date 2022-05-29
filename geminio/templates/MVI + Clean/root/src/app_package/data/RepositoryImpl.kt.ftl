package ${packageName}.${featurePackageName}.data

import ${packageName}.${featurePackageName}.data.mapper.map
import ${packageName}.${featurePackageName}.domain.dependencies.${featureName}Repository
import ${packageName}.${featurePackageName}.domain.entity.${featureName}Domain
import com.itrocket.core.base.CoreDispatchers

class ${featureName}RepositoryImpl(private val coreDispatchers: CoreDispatchers) : ${featureName}Repository {

}
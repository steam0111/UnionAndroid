package ${packageName}.${featurePackageName}.data.mapper

import ${packageName}.${featurePackageName}.domain.entity.${featureName}Domain

fun List<Any>.map(): List<${featureName}Domain> = map {
    ${featureName}Domain()
}
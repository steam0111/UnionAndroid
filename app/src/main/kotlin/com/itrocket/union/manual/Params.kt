package com.itrocket.union.manual

data class Params(val paramList: List<ParamDomain>) {

    private val listNotDefaultParams by lazy {
        listOf(
            ManualType.LOCATION,
            ManualType.STATUS,
            ManualType.DATE
        )
    }

    fun getOrganizationId(): String? {
        return paramList.find { it.type == ManualType.ORGANIZATION }?.id
    }

    fun getLocationIds(): List<String>? {
        return (paramList.find { it.type == ManualType.LOCATION } as? LocationParamDomain)?.ids
    }

    fun getExploitingId(): String? {
        return paramList.find { it.type == ManualType.EXPLOITING }?.id
    }

    fun getMolId(): String? {
        return paramList.find { it.type == ManualType.MOL }?.id
    }

    fun filterNotEmpty(): List<ParamDomain> {
        return paramList.filterNot { it.id.isBlank() }
    }

    /**
     * Флаг, указывающий на надобность открывать дефолтный экран выбора параметров SelectParamComposeFragment
     */
    fun isDefaultParamType(paramDomain: ParamDomain) = !listNotDefaultParams.contains(paramDomain.type)
}
package com.itrocket.union.manual

data class Params(val paramList: List<ParamDomain>) {

    private val listNotDefaultParams by lazy {
        listOf(
            ManualType.LOCATION,
            ManualType.DATE,
            ManualType.LOCATION_TO,
            ManualType.LOCATION_FROM,
            ManualType.RELOCATION_LOCATION_TO
        )
    }

    fun getOrganizationId(): String? {
        return paramList.find { it.type == ManualType.ORGANIZATION }?.id
    }

    fun getExploitingId(): String? {
        return paramList.find { it.type == ManualType.EXPLOITING }?.id
    }

    fun getMolId(): String? {
        return paramList.find { it.type == ManualType.MOL }?.id
    }

    fun filterNotEmpty(): List<ParamDomain> {
        return paramList.filterNot { it.id.isNullOrBlank() }
    }

    /**
     * Флаг, указывающий на надобность открывать дефолтный экран выбора параметров SelectParamComposeFragment
     */
    fun isDefaultParamType(paramDomain: ParamDomain) =
        !listNotDefaultParams.contains(paramDomain.type)
}
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

    fun getMolId(): String? {
        return paramList.find { it.type == ManualType.MOL }?.id
    }

    /**
     * Флаг, указывающий на надобность открывать дефолтный экран выбора параметров SelectParamComposeFragment
     */
    fun isDefaultParamType(paramDomain: ParamDomain) =
        !listNotDefaultParams.contains(paramDomain.type)
}
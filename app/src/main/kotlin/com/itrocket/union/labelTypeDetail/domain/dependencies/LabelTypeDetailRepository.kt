package com.itrocket.union.labelTypeDetail.domain.dependencies

import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import com.itrocket.union.labelTypeDetail.domain.entity.LabelTypeDetailDomain

interface LabelTypeDetailRepository {

    suspend fun getLabelTypeById(id: String): LabelTypeDetailDomain
}
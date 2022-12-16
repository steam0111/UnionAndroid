package com.itrocket.union.labelTypeDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class LabelTypeDetailDomain(val name: String, val listInfo: List<ObjectInfoDomain>)
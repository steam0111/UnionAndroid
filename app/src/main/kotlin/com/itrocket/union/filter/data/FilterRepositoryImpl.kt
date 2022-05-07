package com.itrocket.union.filter.data

import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.domain.entity.FilterValueType

class FilterRepositoryImpl : FilterRepository {
    override fun getFilters(): List<FilterDomain> {
        return listOf(
            FilterDomain(
                name = "Организация",
                valueList = listOf(
                    "Все",
                    "ООО «Грандмастер — Юниор»",
                    "ОАО «Бестиарий»",
                    "ОАО «Интер АйДи»"
                ),
                filterValueType = FilterValueType.MULTI_SELECT_LIST
            ),
            FilterDomain(
                name = "Ответственный",
                valueList = listOf(
                    "JanitorYoke",
                    "ParadoxColor",
                    "GoldLegacy",
                    "SkinPorterMine"
                ),
                filterValueType = FilterValueType.MULTI_SELECT_LIST
            ),
            FilterDomain(
                name = "Эксплуатирующий",
                valueList = listOf(
                    "JanitorYoke",
                    "ParadoxColor",
                    "GoldLegacy",
                    "SkinPorterMine"
                ),
                filterValueType = FilterValueType.MULTI_SELECT_LIST
            ),
            FilterDomain(
                name = "Местоположение",
                filterValueType = FilterValueType.INPUT
            ),
            FilterDomain(
                name = "Статус",
                filterValueType = FilterValueType.SINGLE_SELECT_LIST,
                valueList = listOf("Не найден", "Найден")
            ),
            FilterDomain(
                name = "Дата заявки",
                values = listOf("За все время"),
                valueList = listOf(
                    "За все время",
                    "За сегодня",
                    "За 2 дня",
                    "За 3 дня",
                    "За неделю"
                ),
                filterValueType = FilterValueType.SINGLE_SELECT_LIST
            ),
        )
    }
}
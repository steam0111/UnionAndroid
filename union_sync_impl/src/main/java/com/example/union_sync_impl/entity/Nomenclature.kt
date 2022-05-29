package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItem

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = NomenclatureGroup::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("nomenclatureGroupId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )
    ],
    tableName = "nomenclature"
)
class Nomenclature(
    override var catalogItemName: String,
    var number: String,
    var nomenclatureGroupId: Long
) : CatalogItem()
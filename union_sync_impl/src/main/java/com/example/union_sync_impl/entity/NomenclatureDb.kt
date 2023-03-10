package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = NomenclatureGroupDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("nomenclatureGroupId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )
    ],
    tableName = "nomenclature"
)
class NomenclatureDb(
    id: String,
    cancel: Boolean? = false,
    override var catalogItemName: String,
    var number: String,
    val nomenclatureGroupId: String?,
    val name: String,
    val barcode: String?,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)
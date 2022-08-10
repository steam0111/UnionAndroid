package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.core.SyncItemDb

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
    override var catalogItemName: String,
    var number: String,
    val nomenclatureGroupId: String?,
    val name: String,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)
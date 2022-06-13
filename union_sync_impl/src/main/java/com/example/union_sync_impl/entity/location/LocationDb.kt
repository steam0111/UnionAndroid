package com.example.union_sync_impl.entity.location

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LocationDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("parentId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )
    ],
    tableName = "location"
)
class LocationDb(override var catalogItemName: String, var parentId: Long? = null) : CatalogItemDb()
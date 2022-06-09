package com.example.union_sync_impl.entity.location

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItem

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("parentId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )
    ],
    tableName = "location"
)
class Location(override var catalogItemName: String, var parentId: Long? = null) : CatalogItem()
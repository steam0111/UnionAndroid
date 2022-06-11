package com.example.union_sync_impl.entity.location

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LocationDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("ancestorLocationId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        ),
        ForeignKey(
            entity = LocationDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("descendantLocationId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )
    ],
    tableName = "locationPath"
)
class LocationPath(
    var ancestorLocationId: Long? = null,
    var descendantLocationId: Long,
) : SyncItemDb()
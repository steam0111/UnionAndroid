package com.example.union_sync_impl.entity.structural

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = StructuralDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("ancestorStructuralId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        ),
        ForeignKey(
            entity = StructuralDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("descendantStructuralId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )
    ],
    tableName = "structuralPath"
)
data class StructuralPathDb(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int = 0,
    var descendantStructuralId: String,
    var ancestorStructuralId: String? = null
)
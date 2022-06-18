package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.location.LocationDb

@Entity(
    foreignKeys = [ForeignKey(
        entity = OrganizationDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("organizationId"),
        //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
    ),
        ForeignKey(
            entity = LocationDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        ),
        ForeignKey(
            entity = EmployeeDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("molId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        ),
        ForeignKey(
            entity = EmployeeDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("exploitingId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        ),
        ForeignKey(
            entity = NomenclatureDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("nomenclatureId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        ),
        ForeignKey(
            entity = NomenclatureGroupDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("nomenclatureGroupId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )], tableName = "accounting_objects"
)
class AccountingObjectDb(
    id: String,
    override var catalogItemName: String,
    val organizationId: String?,
    val locationId: String?,
    val molId: String?,
    val exploitingId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val barcodeValue: String?,
    val name: String,
    val rfidValue: String?
) : CatalogItemDb(id)
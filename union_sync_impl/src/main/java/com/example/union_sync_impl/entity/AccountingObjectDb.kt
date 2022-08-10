package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.location.LocationDb

@Entity(
    foreignKeys = [
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
    val locationId: String?,
    val providerId: String?,
    val molId: String?,
    val producerId: String?,
    val exploitingId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val barcodeValue: String?,
    val name: String,
    val rfidValue: String?,
    val factoryNumber: String?,
    val inventoryNumber: String?,
    val status: AccountingObjectStatusDb?,
    val statusId: String?,
    val equipmentTypeId: String?,
    val actualPrice: String?,
    val count: Int?,
    val commissioningDate: String?,
    val internalNumber: String?,
    val model: String?,
    val structuralId: String?,
    val subName: String?,
    val marked: Boolean,
    val forWriteOff: Boolean,
    val writtenOff: Boolean,
    val code: String?,
    val commissioned: Boolean,
    val registered: Boolean,
    val accountingObjectCategoryId: String?,
    val invoiceNumber: String?,
    val nfc: String?,
    val traceable: Boolean,
    userInserted: String?,
    userUpdated: String?,
    updateDate: Long?,
    insertDate: Long?
) : CatalogItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)


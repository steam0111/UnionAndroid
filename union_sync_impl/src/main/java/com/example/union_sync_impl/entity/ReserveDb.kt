package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.core.SyncItemDb
import com.example.union_sync_impl.entity.location.LocationDb
import com.squareup.moshi.Json
import java.math.BigDecimal

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
        ),
        ForeignKey(
            entity = ReceptionItemCategoryDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("receptionItemCategoryId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )], tableName = "reserves"
)
class ReserveDb(
    id: String,
    cancel: Boolean? = false,
    override var catalogItemName: String,
    val locationId: String?,
    val locationTypeId: String?,
    val molId: String?,
    val orderId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val name: String?,
    val count: Long?,
    val receptionItemCategoryId: String?,
    val receptionDocumentNumber: String?,
    val unitPrice: String?,
    val structuralId: String?,
    val traceable: Boolean,
    val invoiceNumber: String?,
    val subName: String?,
    val barcodeValue: String?,
    val labelTypeId: String? = null,
    val bookkeepingInvoice: Int?,
    val consignment: String?,
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
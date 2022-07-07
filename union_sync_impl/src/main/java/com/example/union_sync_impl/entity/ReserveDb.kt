package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.location.LocationDb
import java.math.BigDecimal

@Entity(
    foreignKeys = [ForeignKey(
        entity = OrganizationDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("businessUnitId"),
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
        ),
        ForeignKey(
            entity = DepartmentDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("structuralSubdivisionId"),
            //onDelete = ForeignKey.CASCADE TODO выставить посже когда появиться больше требования к бд
        )], tableName = "reserves"
)
class ReserveDb(
    id: String,
    override var catalogItemName: String,
    val locationId: String?,
    val molId: String?,
    val orderId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val businessUnitId: String?,
    val name: String?,
    val count: Long?,
    val receptionItemCategoryId: String?,
    val structuralSubdivisionId: String?,
    val receptionDocumentNumber: String?,
    val unitPrice: String?,
    updateDate: Long?
) : CatalogItemDb(id, updateDate)
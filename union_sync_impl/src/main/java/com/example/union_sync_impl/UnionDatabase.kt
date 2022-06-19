package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.BranchesDao
import com.example.union_sync_impl.dao.CounterpartyDao
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.EquipmentTypesDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.dao.RegionDao
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.BranchesDb
import com.example.union_sync_impl.entity.CounterpartyDb
import com.example.union_sync_impl.entity.DepartmentDb
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.entity.NetworkSyncDb
import com.example.union_sync_impl.entity.NomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import com.example.union_sync_impl.entity.OrganizationDb
import com.example.union_sync_impl.entity.ProducerDb
import com.example.union_sync_impl.entity.RegionDb
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.EquipmentTypesDb
import com.example.union_sync_impl.entity.location.LocationPath
import com.example.union_sync_impl.utils.Converters

@Database(
    entities = [
        NomenclatureGroupDb::class,
        NomenclatureDb::class,
        LocationDb::class,
        LocationPath::class,
        OrganizationDb::class,
        DepartmentDb::class,
        EmployeeDb::class,
        NetworkSyncDb::class,
        BranchesDb::class,
        InventoryDb::class,
        AccountingObjectDb::class,
        RegionDb::class,
        CounterpartyDb::class,
        ProducerDb::class,
        EquipmentTypesDb::class
    ], version = 31
)
@TypeConverters(Converters::class)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
    abstract fun nomenclatureDao(): NomenclatureDao
    abstract fun locationDao(): LocationDao
    abstract fun locationPathDao(): LocationPathDao
    abstract fun organizationDao(): OrganizationDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun networkSyncDao(): NetworkSyncDao
    abstract fun producerDao(): ProducerDao
    abstract fun counterpartyDao(): CounterpartyDao
    abstract fun regionDao(): RegionDao
    abstract fun accountingObjectDao(): AccountingObjectDao
    abstract fun inventorySyncDao(): InventoryDao
    abstract fun branchesDao(): BranchesDao
    abstract fun equipmentTypeDao(): EquipmentTypesDao
}
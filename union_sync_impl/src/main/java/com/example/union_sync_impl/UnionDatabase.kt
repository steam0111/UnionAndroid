package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.DepartmentDb
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.entity.NetworkSyncDb
import com.example.union_sync_impl.entity.NomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import com.example.union_sync_impl.entity.OrganizationDb
import com.example.union_sync_impl.entity.location.LocationDb
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
        InventoryDb::class,
        AccountingObjectDb::class
    ], version = 30
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
    abstract fun accountingObjectDao(): AccountingObjectDao
    abstract fun inventorySyncDao(): InventoryDao
}
package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.union_sync_impl.dao.*
import com.example.union_sync_impl.entity.*
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationPathDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import com.example.union_sync_impl.entity.structural.StructuralDb
import com.example.union_sync_impl.entity.structural.StructuralPathDb
import com.example.union_sync_impl.utils.Converters

@Database(
    entities = [
        NomenclatureGroupDb::class,
        NomenclatureDb::class,
        LocationDb::class,
        LocationPathDb::class,
        EmployeeDb::class,
        NetworkSyncDb::class,
        InventoryDb::class,
        AccountingObjectDb::class,
        CounterpartyDb::class,
        ProducerDb::class,
        EquipmentTypesDb::class,
        LocationTypeDb::class,
        DocumentDb::class,
        ProviderDb::class,
        ReceptionItemCategoryDb::class,
        OrderDb::class,
        ReserveDb::class,
        ActionRecordDb::class,
        ActionRemainsRecordDb::class,
        InventoryRecordDb::class,
        StructuralPathDb::class,
        StructuralDb::class,
        TransitDb::class,
        TransitAccountingObjectRecordDb::class,
        TransitRemainsRecordDb::class,
        EnumDb::class,
        InventoryCheckerDb::class,
        AccountingObjectSimpleAdditionalFieldDb::class,
        AccountingObjectVocabularyAdditionalFieldDb::class,
        SimpleAdditionalFieldDb::class,
        VocabularyAdditionalFieldDb::class,
        VocabularyAdditionalFieldValueDb::class,
        AccountingObjectSimpleCharacteristicsDb::class,
        AccountingObjectVocabularyCharacteristicsDb::class,
        SimpleCharacteristicDb::class,
        VocabularyCharacteristicDb::class,
        VocabularyCharacteristicValueDb::class,
        LabelTypeDb::class
    ], version = 119
)
@TypeConverters(Converters::class)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun labelTypeDao(): LabelTypeDao
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
    abstract fun nomenclatureDao(): NomenclatureDao
    abstract fun locationDao(): LocationDao
    abstract fun locationPathDao(): LocationPathDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun networkSyncDao(): NetworkSyncDao
    abstract fun producerDao(): ProducerDao
    abstract fun counterpartyDao(): CounterpartyDao
    abstract fun accountingObjectDao(): AccountingObjectDao
    abstract fun inventorySyncDao(): InventoryDao
    abstract fun equipmentTypeDao(): EquipmentTypeDao
    abstract fun documentDao(): DocumentDao
    abstract fun providerDao(): ProviderDao
    abstract fun receptionItemCategoryDao(): ReceptionItemCategoryDao
    abstract fun orderDao(): OrderDao
    abstract fun reserveDao(): ReserveDao
    abstract fun actionRecordDao(): ActionRecordDao
    abstract fun actionRemainsRecordDao(): ActionRemainsRecordDao
    abstract fun inventoryRecordDao(): InventoryRecordDao
    abstract fun structuralDao(): StructuralDao
    abstract fun structuralPathDao(): StructuralPathDao
    abstract fun transitDao(): TransitDao
    abstract fun transitAccountingObjectRecordDao(): TransitAccountingObjectRecordDao
    abstract fun transitRemainsRecordDao(): TransitRemainsRecordDao
    abstract fun enumsDao(): EnumsDao
    abstract fun inventoryChecker(): InventoryCheckerDao
    abstract fun accountingObjectSimpleAdditionalFieldDao(): AccountingObjectSimpleAdditionalFieldDao
    abstract fun accountingObjectVocabularyAdditionalFieldDao(): AccountingObjectVocabularyAdditionalFieldDao
    abstract fun simpleAdditionalFieldDao(): SimpleAdditionalFieldDao
    abstract fun vocabularyAdditionalFieldDao(): VocabularyAdditionalFieldDao
    abstract fun vocabularyAdditionalFieldValueDao(): VocabularyAdditionalFieldValueDao
    abstract fun accountingObjectsVocabularyCharacteristicsDao(): AccountingObjectsVocabularyCharacteristicsDao
    abstract fun accountingObjectsSimpleCharacteristicsDao(): AccountingObjectsSimpleCharacteristicsDao
    abstract fun simpleCharacteristicsDao(): SimpleCharacteristicDao
    abstract fun vocabularyCharacteristicsDao(): VocabularyCharacteristicDao
    abstract fun vocabularyCharacteristicValueDao(): VocabularyCharacteristicValueDao

}
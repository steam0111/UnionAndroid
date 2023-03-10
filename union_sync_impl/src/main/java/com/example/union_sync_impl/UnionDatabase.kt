package com.example.union_sync_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.AccountingObjectSimpleAdditionalFieldDao
import com.example.union_sync_impl.dao.AccountingObjectUnionImageDao
import com.example.union_sync_impl.dao.AccountingObjectVocabularyAdditionalFieldDao
import com.example.union_sync_impl.dao.AccountingObjectsSimpleCharacteristicsDao
import com.example.union_sync_impl.dao.ActionRecordDao
import com.example.union_sync_impl.dao.ActionRemainsRecordDao
import com.example.union_sync_impl.dao.CounterpartyDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.EnumsDao
import com.example.union_sync_impl.dao.EquipmentTypeDao
import com.example.union_sync_impl.dao.InventoryCheckerDao
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.LabelTypeDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrderDao
import com.example.union_sync_impl.dao.ProducerDao
import com.example.union_sync_impl.dao.ProviderDao
import com.example.union_sync_impl.dao.ReceptionItemCategoryDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.SimpleAdditionalFieldDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.StructuralPathDao
import com.example.union_sync_impl.dao.TransitAccountingObjectRecordDao
import com.example.union_sync_impl.dao.TransitDao
import com.example.union_sync_impl.dao.TransitRemainsRecordDao
import com.example.union_sync_impl.dao.AccountingObjectsVocabularyCharacteristicsDao
import com.example.union_sync_impl.dao.EmployeeWorkPlaceDao
import com.example.union_sync_impl.dao.InventoryNomenclatureRecordDao
import com.example.union_sync_impl.dao.InventoryNomenclatureRecordRfidDao
import com.example.union_sync_impl.dao.SimpleCharacteristicDao
import com.example.union_sync_impl.dao.SyncDao
import com.example.union_sync_impl.dao.TerminalInfoDao
import com.example.union_sync_impl.dao.TerminalRemainsNumeratorDao
import com.example.union_sync_impl.dao.VocabularyAdditionalFieldDao
import com.example.union_sync_impl.dao.VocabularyAdditionalFieldValueDao
import com.example.union_sync_impl.dao.VocabularyCharacteristicDao
import com.example.union_sync_impl.dao.VocabularyCharacteristicValueDao
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.AccountingObjectSimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.AccountingObjectSimpleCharacteristicsDb
import com.example.union_sync_impl.entity.AccountingObjectUnionImageDb
import com.example.union_sync_impl.entity.AccountingObjectVocabularyAdditionalFieldDb
import com.example.union_sync_impl.entity.AccountingObjectVocabularyCharacteristicsDb
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import com.example.union_sync_impl.entity.CounterpartyDb
import com.example.union_sync_impl.entity.DocumentDb
import com.example.union_sync_impl.entity.EmployeeDb
import com.example.union_sync_impl.entity.EmployeeWorkPlaceDb
import com.example.union_sync_impl.entity.EnumDb
import com.example.union_sync_impl.entity.EquipmentTypesDb
import com.example.union_sync_impl.entity.InventoryCheckerDb
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.entity.InventoryNomenclatureRecordDb
import com.example.union_sync_impl.entity.InventoryNomenclatureRecordRfidDb
import com.example.union_sync_impl.entity.InventoryNomenclatureRecordUpdate
import com.example.union_sync_impl.entity.InventoryRecordDb
import com.example.union_sync_impl.entity.LabelTypeDb
import com.example.union_sync_impl.entity.NetworkSyncDb
import com.example.union_sync_impl.entity.NomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import com.example.union_sync_impl.entity.OrderDb
import com.example.union_sync_impl.entity.ProducerDb
import com.example.union_sync_impl.entity.ProviderDb
import com.example.union_sync_impl.entity.ReceptionItemCategoryDb
import com.example.union_sync_impl.entity.ReserveDb
import com.example.union_sync_impl.entity.SimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.SimpleCharacteristicDb
import com.example.union_sync_impl.entity.TerminalInfoDb
import com.example.union_sync_impl.entity.TerminalRemainsNumeratorDb
import com.example.union_sync_impl.entity.TransitAccountingObjectRecordDb
import com.example.union_sync_impl.entity.TransitDb
import com.example.union_sync_impl.entity.TransitRemainsRecordDb
import com.example.union_sync_impl.entity.VocabularyAdditionalFieldDb
import com.example.union_sync_impl.entity.VocabularyAdditionalFieldValueDb
import com.example.union_sync_impl.entity.VocabularyCharacteristicDb
import com.example.union_sync_impl.entity.VocabularyCharacteristicValueDb
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
        TerminalInfoDb::class,
        LabelTypeDb::class,
        TerminalRemainsNumeratorDb::class,
        AccountingObjectUnionImageDb::class,
        EmployeeWorkPlaceDb::class,
        InventoryNomenclatureRecordDb::class,
        InventoryNomenclatureRecordRfidDb::class
    ], version = 140
)
@TypeConverters(Converters::class)
abstract class UnionDatabase : RoomDatabase() {
    abstract fun terminalRemainsNumeratorDao(): TerminalRemainsNumeratorDao
    abstract fun labelTypeDao(): LabelTypeDao
    abstract fun nomenclatureGroupDao(): NomenclatureGroupDao
    abstract fun accountingObjectUnionImageDao(): AccountingObjectUnionImageDao
    abstract fun nomenclatureDao(): NomenclatureDao
    abstract fun locationDao(): LocationDao
    abstract fun locationPathDao(): LocationPathDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun terminalInfoDao(): TerminalInfoDao
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
    abstract fun employeeWorkPlaceDao(): EmployeeWorkPlaceDao
    abstract fun syncDao(): SyncDao
    abstract fun inventoryNomenclatureRecordDao(): InventoryNomenclatureRecordDao
    abstract fun inventoryNomenclatureRecordRfidDao(): InventoryNomenclatureRecordRfidDao
}
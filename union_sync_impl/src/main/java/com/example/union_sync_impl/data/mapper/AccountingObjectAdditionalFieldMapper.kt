package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectAdditionalFieldSyncEntity
import com.example.union_sync_api.entity.AccountingObjectCharacteristicSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectSimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.AccountingObjectVocabularyAdditionalFieldDb
import com.example.union_sync_impl.entity.FullAccountingObjectSimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.FullAccountingObjectVocabularyAdditionalFieldDb
import com.example.union_sync_impl.entity.SimpleAdditionalFieldDb
import com.example.union_sync_impl.entity.AccountingObjectSimpleCharacteristicsDb
import com.example.union_sync_impl.entity.VocabularyAdditionalFieldDb
import com.example.union_sync_impl.entity.VocabularyAdditionalFieldValueDb
import com.example.union_sync_impl.entity.AccountingObjectVocabularyCharacteristicsDb
import com.example.union_sync_impl.entity.FullAccountingObjectSimpleCharacteristicDb
import com.example.union_sync_impl.entity.FullAccountingObjectVocabularyCharacteristicDb
import com.example.union_sync_impl.entity.SimpleCharacteristicDb
import com.example.union_sync_impl.entity.VocabularyCharacteristicDb
import com.example.union_sync_impl.entity.VocabularyCharacteristicValueDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.AccountingObjectCharacteristicValueDtoV2
import org.openapitools.client.models.AccountingObjectSimpleAdditionalFieldValueDtoV2
import org.openapitools.client.models.AccountingObjectVocabularyAdditionalFieldValueDtoV2
import org.openapitools.client.models.AccountingObjectVocabularyCharacteristicValueDtoV2
import org.openapitools.client.models.CharacteristicDtoV2
import org.openapitools.client.models.SimpleAdditionalFieldDtoV2
import org.openapitools.client.models.VocabularyAdditionalFieldDtoV2
import org.openapitools.client.models.VocabularyAdditionalFieldValueDtoV2
import org.openapitools.client.models.VocabularyCharacteristicDtoV2
import org.openapitools.client.models.VocabularyCharacteristicValueDtoV2

fun AccountingObjectSimpleAdditionalFieldValueDtoV2.toDb() =
    AccountingObjectSimpleAdditionalFieldDb(
        id = id,
        insertDate = getMillisDateFromServerFormat(dateInsert),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        userUpdated = userUpdated,
        userInserted = userInserted,
        catalogItemName = catalogItemName.orEmpty(),
        value = value,
        accountingObjectId = accountingObjectId,
        simpleAdditionalFieldId = simpleAdditionalFieldId,
        cancel = deleted
    )


fun AccountingObjectVocabularyAdditionalFieldValueDtoV2.toDb() =
    AccountingObjectVocabularyAdditionalFieldDb(
        id = id,
        insertDate = getMillisDateFromServerFormat(dateInsert),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        userUpdated = userUpdated,
        userInserted = userInserted,
        catalogItemName = catalogItemName.orEmpty(),
        accountingObjectId = accountingObjectId,
        vocabularyAdditionalFieldValueId = vocabularyAdditionalFieldValueId,
        vocabularyAdditionalFieldId = vocabularyAdditionalFieldId,
        cancel = deleted
    )


fun SimpleAdditionalFieldDtoV2.toDb() = SimpleAdditionalFieldDb(
    id = id,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    catalogItemName = catalogItemName.orEmpty(),
    name = name,
    cancel = deleted
)


fun VocabularyAdditionalFieldDtoV2.toDb() = VocabularyAdditionalFieldDb(
    id = id,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    catalogItemName = catalogItemName.orEmpty(),
    name = name,
    cancel = deleted
)


fun VocabularyAdditionalFieldValueDtoV2.toDb() = VocabularyAdditionalFieldValueDb(
    id = id,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    catalogItemName = catalogItemName.orEmpty(),
    value = value,
    cancel = deleted
)

fun FullAccountingObjectSimpleAdditionalFieldDb.toSyncEntity() =
    AccountingObjectAdditionalFieldSyncEntity(
        id = accountingObjectSimpleAdditionalFieldDb.id,
        accountingObjectId = accountingObjectSimpleAdditionalFieldDb.accountingObjectId,
        name = name?.name,
        value = accountingObjectSimpleAdditionalFieldDb.value
    )

fun FullAccountingObjectVocabularyAdditionalFieldDb.toSyncEntity() =
    AccountingObjectAdditionalFieldSyncEntity(
        id = accountingObjectVocabularyAdditionalFieldDb.id,
        accountingObjectId = accountingObjectVocabularyAdditionalFieldDb.accountingObjectId,
        name = name?.name,
        value = value?.value
    )

fun AccountingObjectCharacteristicValueDtoV2.toDb() =
    AccountingObjectSimpleCharacteristicsDb(
        id = id,
        insertDate = getMillisDateFromServerFormat(dateInsert),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        userUpdated = userUpdated,
        userInserted = userInserted,
        catalogItemName = catalogItemName.orEmpty(),
        value = value,
        accountingObjectId = accountingObjectId,
        simpleCharacteristicId = characteristicId,
        cancel = deleted
    )


fun AccountingObjectVocabularyCharacteristicValueDtoV2.toDb() =
    AccountingObjectVocabularyCharacteristicsDb(
        id = id,
        insertDate = getMillisDateFromServerFormat(dateInsert),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        userUpdated = userUpdated,
        userInserted = userInserted,
        catalogItemName = catalogItemName.orEmpty(),
        accountingObjectId = accountingObjectId,
        vocabularyCharacteristicValueId = vocabularyCharacteristicValueId,
        vocabularyCharacteristicId = vocabularyCharacteristicId,
        cancel = deleted
    )

fun CharacteristicDtoV2.toDb() = SimpleCharacteristicDb(
    id = id,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    catalogItemName = catalogItemName.orEmpty(),
    name = name,
    cancel = deleted
)

fun VocabularyCharacteristicDtoV2.toDb() = VocabularyCharacteristicDb(
    id = id,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    catalogItemName = catalogItemName.orEmpty(),
    name = name,
    cancel = deleted
)


fun VocabularyCharacteristicValueDtoV2.toDb() = VocabularyCharacteristicValueDb(
    id = id,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    catalogItemName = catalogItemName.orEmpty(),
    value = name,
    cancel = deleted
)

fun FullAccountingObjectSimpleCharacteristicDb.toSyncEntity() =
    AccountingObjectCharacteristicSyncEntity(
        id = accountingObjectSimpleCharacteristicDb.id,
        accountingObjectId = accountingObjectSimpleCharacteristicDb.accountingObjectId,
        name = name?.name,
        value = accountingObjectSimpleCharacteristicDb.value
    )

fun FullAccountingObjectVocabularyCharacteristicDb.toSyncEntity() =
    AccountingObjectCharacteristicSyncEntity(
        id = accountingObjectVocabularyCharacteristicDb.id,
        accountingObjectId = accountingObjectVocabularyCharacteristicDb.accountingObjectId,
        name = name?.name,
        value = value?.value
    )
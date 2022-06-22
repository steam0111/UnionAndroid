package com.example.union_sync_impl.data

import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrePopulateRepository(
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val nomenclatureDao: NomenclatureDao,
    private val unionDatabase: UnionDatabase
) {
    init {
        /*
         * TODO Используется для тестирования бд, в релизной сборке удалить
         * */
        GlobalScope.launch(Dispatchers.IO) {
//            unionDatabase.clearAllTables()
//
//            nomenclatureGroupDao.insertAll(
//                NomenclatureGroup("Столы"),
//                NomenclatureGroup("Стулья"),
//                NomenclatureGroup("Мобильные телефоны"),
//                NomenclatureGroup("Мониторы"),
//                NomenclatureGroup("Шкафы"),
//            )
//            val groups = nomenclatureGroupDao.getAll()
//            groups.forEachIndexed { index, nomenclatureGroupDao ->
//                when (index) {
//                    0 -> {
//                        nomenclatureDao.insertAll(
//                            Nomenclature("Швейцарский стол", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Польский стол", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Деревянный стол", "10", nomenclatureGroupDao.id),
//                        )
//                    }
//                    1 -> {
//                        nomenclatureDao.insertAll(
//                            Nomenclature("Швейцарский стул", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Польский стул", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Деревянный стул", "10", nomenclatureGroupDao.id),
//                        )
//                    }
//                    2 -> {
//                        nomenclatureDao.insertAll(
//                            Nomenclature("Iphone X", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Ксяоми P90", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Samsung s30", "10", nomenclatureGroupDao.id),
//                        )
//                    }
//                    3 -> {
//                        nomenclatureDao.insertAll(
//                            Nomenclature("LG VGA 1000", "10", nomenclatureGroupDao.id),
//                            Nomenclature("Samsung Super HD", "10", nomenclatureGroupDao.id),
//                            Nomenclature("LG 500", "10", nomenclatureGroupDao.id),
//                        )
//                    }
//                }
//            }
        }
    }
}
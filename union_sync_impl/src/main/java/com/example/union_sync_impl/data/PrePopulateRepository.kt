package com.example.union_sync_impl.data

import com.example.union_sync_impl.UnionDatabase
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.entity.Nomenclature
import com.example.union_sync_impl.entity.NomenclatureGroup
import com.example.union_sync_impl.entity.location.Location
import com.example.union_sync_impl.entity.location.LocationPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrePopulateRepository(
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val nomenclatureDao: NomenclatureDao,
    private val locationDao: LocationDao,
    private val locationPathDao: LocationPathDao,
    private val unionDatabase: UnionDatabase
) {
    init {
        /*
         * TODO Используется для тестирования бд, в релизной сборке удалить
         * */
        GlobalScope.launch(Dispatchers.IO) {
            unionDatabase.clearAllTables()

            prePopulateNomenclature()
            prePopulateLocation()
        }
    }

    private suspend fun prePopulateLocation() {
        val locationsPaths = mutableListOf<LocationPath>()

        locationDao.insertAll(
            Location("Головной офис 1", parentId = null),
            Location("Региональная сеть 1", parentId = null),
            Location("Мирова сеть 1", parentId = null)
        )

        val rootLocations = locationDao.getAll(parentId = null)

        // Root
        rootLocations.forEach { location ->
            locationsPaths.add(LocationPath(ancestorLocationId = null, descendantLocationId = location.id))

            addLocationDeepLevel(location)

            val firstDeep = locationDao.getAll(parentId = location.id)

            // First layer
            firstDeep.forEach { firstDeepLocation ->
                locationsPaths.add(LocationPath(ancestorLocationId = location.id, descendantLocationId = firstDeepLocation.id))
                locationsPaths.add(LocationPath(ancestorLocationId = null, descendantLocationId = firstDeepLocation.id))

                addLocationDeepLevel(firstDeepLocation)

                val secondDeep = locationDao.getAll(parentId = firstDeepLocation.id)

                secondDeep.forEach { secondDeepLocation ->
                    locationsPaths.add(LocationPath(ancestorLocationId = firstDeepLocation.id, descendantLocationId = secondDeepLocation.id))
                    locationsPaths.add(LocationPath(ancestorLocationId = location.id, descendantLocationId = secondDeepLocation.id))
                }
            }
        }

        locationPathDao.insertAll(*locationsPaths.toTypedArray())
    }

    private suspend fun addLocationDeepLevel(location: Location) {
        locationDao.insertAll(
            Location(location.catalogItemName + " 1", parentId = location.id),
            Location(location.catalogItemName + " 1", parentId = location.id),
            Location(location.catalogItemName + " 1", parentId = location.id)
        )
    }

    private suspend fun prePopulateNomenclature() {
        nomenclatureGroupDao.insertAll(
            NomenclatureGroup("Столы"),
            NomenclatureGroup("Стулья"),
            NomenclatureGroup("Мобильные телефоны"),
            NomenclatureGroup("Мониторы"),
        )
        val groups = nomenclatureGroupDao.getAll()
        groups.forEachIndexed { index, nomenclatureGroupDao ->
            when (index) {
                0 -> {
                    nomenclatureDao.insertAll(
                        Nomenclature("Швейцарский стол", "10", nomenclatureGroupDao.id),
                        Nomenclature("Польский стол", "10", nomenclatureGroupDao.id),
                        Nomenclature("Деревянный стол", "10", nomenclatureGroupDao.id),
                    )
                }
                1 -> {
                    nomenclatureDao.insertAll(
                        Nomenclature("Швейцарский стул", "10", nomenclatureGroupDao.id),
                        Nomenclature("Польский стул", "10", nomenclatureGroupDao.id),
                        Nomenclature("Деревянный стул", "10", nomenclatureGroupDao.id),
                    )
                }
                2 -> {
                    nomenclatureDao.insertAll(
                        Nomenclature("Iphone X", "10", nomenclatureGroupDao.id),
                        Nomenclature("Ксяоми P90", "10", nomenclatureGroupDao.id),
                        Nomenclature("Samsung s30", "10", nomenclatureGroupDao.id),
                    )
                }
                3 -> {
                    nomenclatureDao.insertAll(
                        Nomenclature("LG VGA 1000", "10", nomenclatureGroupDao.id),
                        Nomenclature("Samsung Super HD", "10", nomenclatureGroupDao.id),
                        Nomenclature("LG 500", "10", nomenclatureGroupDao.id),
                    )
                }
            }
        }
    }
}
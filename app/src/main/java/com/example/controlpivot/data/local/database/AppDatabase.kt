package com.example.controlpivot.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.local.database.dao.ClimateDao
import com.example.controlpivot.data.local.database.dao.PivotControlDao
import com.example.controlpivot.data.local.database.dao.PivotMachineDao
import com.example.controlpivot.data.local.database.dao.SectorControlDao
import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.utils.Converters

@Database(
    entities = [
        PivotMachineEntity::class,
        Climate::class,
        PivotControlEntity::class,
        SectorControl::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pivotMachineDao(): PivotMachineDao
    abstract fun climateDao(): ClimateDao
    abstract fun pivotControlDao(): PivotControlDao
    abstract fun sectorControlDao(): SectorControlDao
}
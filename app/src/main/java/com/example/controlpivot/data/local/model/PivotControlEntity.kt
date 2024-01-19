package com.example.controlpivot.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pivot_control")
data class PivotControlEntity(
    @PrimaryKey
    val id: Int,
    val idPivot: Int,
    val progress: Float,
    val isRunning: Boolean,
    val stateBomb: Boolean,
    val wayToPump: Boolean,
    val turnSense: Boolean,
)

@Entity(tableName = "sector_control")
data class SectorControl(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "sector_id")
    val sector_id: Int,
    val irrigateState: Boolean,
    val dosage: Int,
    val motorVelocity: Int,
    @ColumnInfo(name = "sector_control_id")
    val sector_control_id: Int
)



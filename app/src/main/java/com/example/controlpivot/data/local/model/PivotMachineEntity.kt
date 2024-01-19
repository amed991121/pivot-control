package com.example.controlpivot.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.controlpivot.data.remote.model.PivotMachine

@Entity(tableName = "pivot_machines")
data class PivotMachineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "remote_id")
    val remoteId: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "name")
    val name: String = "",
    val location: String = "",
    val endowment: Double = 0.0,
    val flow: Double = 0.0,
    val pressure: Double = 0.0,
    val length: Double = 0.0,
    val area: Double = 0.0,
    val power: Double = 0.0,
    val speed: Double = 0.0,
    val efficiency: Double = 0.0,
    val isSave: Boolean = true,
)

fun PivotMachine.toLocal() = PivotMachineEntity(
    id,
    id,
    name,
    location,
    endowment,
    flow,
    pressure,
    length,
    area,
    power,
    speed,
    efficiency
)
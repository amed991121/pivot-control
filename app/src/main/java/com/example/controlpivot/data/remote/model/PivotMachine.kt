package com.example.controlpivot.data.remote.model

import com.example.controlpivot.data.local.model.PivotMachineEntity

data class PivotMachine(
    val id: Int,
    val name: String,
    val location: String,
    val endowment: Double,
    val flow: Double,
    val pressure: Double,
    val length: Double,
    val area: Double,
    val power: Double,
    val speed: Double,
    val efficiency: Double,
)

fun PivotMachineEntity.toNetwork() = PivotMachine(
    remoteId,
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
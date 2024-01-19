package com.example.controlpivot.ui.model

data class MachineModel(
    val id: Int,
    val name: String,
    val endowment: Long,
    val flow: Long,
    val pressure: Long,
    val length: Long,
    val area: Long,
    val power: Long,
    val speed: Long,
    val efficiency: Long,
)

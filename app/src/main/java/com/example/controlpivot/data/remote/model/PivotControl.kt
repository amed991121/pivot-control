package com.example.controlpivot.data.remote.model

import com.example.controlpivot.data.local.model.SectorControl

data class PivotControl(
    val id: Int,
    val idPivot: Int,
    val progress: Float,
    val isRunning: Boolean,
    val stateBomb: Boolean,
    val wayToPump: Boolean,
    val turnSense: Boolean,
    val sectorControlList: List<SectorControl>
)

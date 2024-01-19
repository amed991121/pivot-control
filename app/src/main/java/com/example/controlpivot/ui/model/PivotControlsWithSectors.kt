package com.example.controlpivot.ui.model

import com.example.controlpivot.data.local.model.SectorControl

data class PivotControlsWithSectors(
    val id: Int,
    val idPivot: Int,
    val progress: Float,
    val isRunning: Boolean,
    val stateBomb: Boolean,
    val wayToPump: Boolean,
    val turnSense: Boolean,
    val sectorList : List<SectorControl>
)

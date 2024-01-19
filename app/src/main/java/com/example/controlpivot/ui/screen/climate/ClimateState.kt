package com.example.controlpivot.ui.screen.climate

import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.ui.model.IdPivotModel

data class ClimateState (
    val climates: List<Climate> = listOf(),
    var currentClimate: Climate? = null,
    var selectedIdPivot: Int = 0,
    var idPivotList: List<IdPivotModel> = listOf(),
    var isLoading: Boolean = false,
)
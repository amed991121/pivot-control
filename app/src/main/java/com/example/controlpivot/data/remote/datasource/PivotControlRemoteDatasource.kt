package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.remote.model.PivotControl
import com.example.controlpivot.utils.Resource

interface PivotControlRemoteDatasource {

    suspend fun getPivotControls(): Resource<List<PivotControl>>

    suspend fun getSectorControls(): Resource<List<SectorControl>>

    suspend fun insertPivotControl(pivot: PivotControlEntity): Resource<Int>

    suspend fun insertSectorControl(sector: SectorControl): Resource<Int>

    suspend fun updatePivotControl(pivot: PivotControlEntity): Resource<Int>

    suspend fun updateSectorControl(sector: SectorControl): Resource<Int>

    suspend fun deletePivotControl(pivotId: Int): Resource<Int>
}
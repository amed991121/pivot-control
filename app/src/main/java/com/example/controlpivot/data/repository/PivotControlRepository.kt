package com.example.controlpivot.data.repository

import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PivotControlRepository {

    suspend fun upsertPivotControls(pivots: List<PivotControlEntity>): Resource<Int>

    suspend fun addPivotControl(pivot: PivotControlEntity): Resource<Int>

    suspend fun upsertSectorControl(sectorControl: SectorControl): Resource<Int>

    suspend fun getPivotControl(id: Int): Resource<PivotControlEntity>

    fun getAllPivotControls(query: String): Flow<Resource<List<PivotControlEntity>>>

    fun getAllSectorControls(query: String): Flow<Resource<List<SectorControl>>>

    fun getSectorsForPivot(query: Int) : Flow<Resource<List<SectorControl>>>

    suspend fun fetchPivotControl(): Resource<Int>

    suspend fun updatePivotControl(pivot: PivotControlEntity): Resource<Int>

    suspend fun updateSectorControl(sectorControl: SectorControl): Resource<Int>

    suspend fun deletePivotControl(id: Int): Resource<Int>
}
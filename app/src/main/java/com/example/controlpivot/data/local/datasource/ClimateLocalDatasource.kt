package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ClimateLocalDatasource {

    suspend fun addClimate(climate: Climate): Resource<Int>

    suspend fun getClimate(id: Int): Resource<Climate>

    suspend fun getClimatesByIdPivot(id: Int): Resource<List<Climate>>

    fun getClimates(query: String): Flow<Resource<List<Climate>>>

    suspend fun upsertClimates(climates: List<Climate>): Resource<Int>

    suspend fun updateClimate(climate: Climate): Resource<Int>

    suspend fun deleteClimate(climate: Climate): Resource<Int>
}
package com.example.controlpivot.data.repository

import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ClimateRepository {

    suspend fun upsertClimates(climates: List<Climate>): Resource<Int>

    suspend fun addClimate(climate: Climate): Resource<Int>

    suspend fun getClimate(id: Int): Resource<Climate>

    suspend fun getClimatesById(id: Int): Resource<List<Climate>>

    fun getAllClimates(query: String): Flow<Resource<List<Climate>>>

    suspend fun fetchClimate(): Resource<Int>

    suspend fun updateClimate(climate: Climate): Resource<Int>

    suspend fun deleteClimate(id: Int): Resource<Int>
}
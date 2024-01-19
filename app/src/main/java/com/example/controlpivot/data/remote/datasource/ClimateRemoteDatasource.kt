package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.utils.Resource

interface ClimateRemoteDatasource {

    suspend fun getClimates(): Resource<List<Climate>>

    suspend fun insertClimate(climate: Climate): Resource<Int>

    suspend fun updateClimate(climate: Climate): Resource<Int>

    suspend fun deleteClimate(climateId: Int): Resource<Int>
}
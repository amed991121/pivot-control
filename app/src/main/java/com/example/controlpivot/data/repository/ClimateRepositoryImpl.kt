package com.example.controlpivot.data.repository

import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.data.local.datasource.ClimateLocalDatasource
import com.example.controlpivot.data.remote.datasource.ClimateRemoteDatasource
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

class ClimateRepositoryImpl(
    private val localDatasource: ClimateLocalDatasource,
    private val remoteDatasource: ClimateRemoteDatasource,
) : ClimateRepository {

    override suspend fun upsertClimates(climates: List<Climate>): Resource<Int> =
        localDatasource.upsertClimates(climates)

    override suspend fun addClimate(climate: Climate): Resource<Int> {
        val response = remoteDatasource.insertClimate(climate)
        if (response is Resource.Error)
            return response
        return localDatasource.addClimate(climate)
    }

    override suspend fun getClimate(id: Int): Resource<Climate> =
        localDatasource.getClimate(id)

    override suspend fun getClimatesById(id: Int): Resource<List<Climate>> =
        localDatasource.getClimatesByIdPivot(id)

    override fun getAllClimates(query: String): Flow<Resource<List<Climate>>> =
        localDatasource.getClimates(query)

    override suspend fun fetchClimate(): Resource<Int> {
        return when (val response = remoteDatasource.getClimates()) {
            is Resource.Error -> Resource.Error(response.message)
            is Resource.Success -> localDatasource.upsertClimates(response.data)
        }
    }

    override suspend fun updateClimate(climate: Climate): Resource<Int> =
        remoteDatasource.updateClimate(climate)

    override suspend fun deleteClimate(id: Int): Resource<Int> =
        remoteDatasource.deleteClimate(id)
}
package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.data.local.database.dao.ClimateDao
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ClimateLocalDatasourceImpl(
    private val climateDao: ClimateDao
): ClimateLocalDatasource {

    override suspend fun addClimate(climate: Climate): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = climateDao.insert(climate)
            if (result > 0L)
                return@withContext Resource.Success(result.toInt())
            Resource.Error(
                Message.StringResource(R.string.add_climate_error)
            )
        }

    override suspend fun getClimate(id: Int): Resource<Climate> =
        withContext(Dispatchers.IO) {
            val result = climateDao.get(id)
            if (result != null)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.climate_not_found)
            )
        }

    override suspend fun getClimatesByIdPivot(id: Int): Resource<List<Climate>> =
        withContext(Dispatchers.IO) {
            val result = climateDao.getByIdPivot(id)
            if (result != null)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.climate_not_found)
            )
        }

    override fun getClimates(query: String): Flow<Resource<List<Climate>>> = flow {
        climateDao.getAll(query).onEach {
            emit(Resource.Success(it))
        }.catch {
            Resource.Error<List<Climate>>(
                Message.StringResource(R.string.get_climates_error)
            )
        }.collect()
    }

    override suspend fun upsertClimates(climates: List<Climate>): Resource<Int> =
        synchronized(this) {
            runBlocking(Dispatchers.IO) {
                val result = climateDao.upsertAll(climates)
                if (result.isEmpty() && climates.isNotEmpty())
                    return@runBlocking Resource.Error<Int>(
                        Message.StringResource(R.string.update_climates_error)
                    )
                Resource.Success(result.size)
            }
        }

    override suspend fun updateClimate(climate: Climate): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = climateDao.update(climate)
            if (result > 0)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.update_climate_error)
            )
        }

    override suspend fun deleteClimate(climate: Climate): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = climateDao.delete(climate)
            if (result > 0)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.delete_climate_error)
            )
        }


}
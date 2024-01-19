package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.common.model.Climate
import com.example.controlpivot.data.remote.MessageBody
import com.example.controlpivot.data.remote.service.ClimateApiService
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClimateRemoteDatasourceImpl(
    private val climateApiService: ClimateApiService
): ClimateRemoteDatasource {

    override suspend fun getClimates(): Resource<List<Climate>> =
        withContext(Dispatchers.IO) {
            try {
                val response = climateApiService.getClimates()
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(
                    Message.DynamicString(
                    Gson().fromJson(
                        response.errorBody()?.charStream(),
                        MessageBody::class.java
                    ).message
                ))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.fetch_climates_error))
            }
        }

    override suspend fun insertClimate(climate: Climate): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = climateApiService.insertClimate(climate)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.add_climate_error))
            }
        }

    override suspend fun updateClimate(climate: Climate): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = climateApiService.updateClimate(climate)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.update_climate_error))
            }
        }

    override suspend fun deleteClimate(climateId: Int): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = climateApiService.deleteClimate(climateId)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.delete_climate_error))
            }
        }
}
package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.remote.model.PivotControl
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.remote.MessageBody
import com.example.controlpivot.data.remote.service.PivotControlApiService
import com.example.controlpivot.data.remote.service.SectorControlApiService
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PivotControlRemoteDatasourceImpl(
    private val pivotControlApiService: PivotControlApiService,
    private val sectorControlApiService: SectorControlApiService
): PivotControlRemoteDatasource {

    override suspend fun getPivotControls(): Resource<List<PivotControl>> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotControlApiService.getPivotControls()
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
                Resource.Error(Message.StringResource(R.string.fetch_pivots_error))
            }
        }

    override suspend fun getSectorControls(): Resource<List<SectorControl>> =
        withContext(Dispatchers.IO) {
            try {
                val response = sectorControlApiService.getSectorControls()
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
                Resource.Error(Message.StringResource(R.string.fetch_sectors_error))
            }
        }

    override suspend fun insertPivotControl(pivot: PivotControlEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotControlApiService.insertPivotControl(pivot)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.add_pivot_error))
            }
        }

    override suspend fun insertSectorControl(sector: SectorControl): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = sectorControlApiService.insertSectorControl(sector)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.add_sector_error))
            }
        }

    override suspend fun updatePivotControl(pivot: PivotControlEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotControlApiService.updatePivotControl(pivot)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.update_pivot_error))
            }
        }

    override suspend fun updateSectorControl(sector: SectorControl): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = sectorControlApiService.updateSectorControl(sector)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.update_sector_error))
            }
        }

    override suspend fun deletePivotControl(pivotId: Int): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotControlApiService.deletePivotControl(pivotId)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.delete_pivot_error))
            }
        }
}
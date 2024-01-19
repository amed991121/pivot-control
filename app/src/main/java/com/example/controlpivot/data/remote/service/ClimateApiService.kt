package com.example.controlpivot.data.remote.service

import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.common.model.Climate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ClimateApiService {

    @GET(AppConstants.CLIMATE_API_PATH)
    suspend fun getClimates(
    ): Response<List<Climate>>

    @POST
    suspend fun insertClimate(
        @Body climate: Climate
    ): Response<Int>

    @PUT
    suspend fun updateClimate(
        @Body climate: Climate
    ): Response<Int>

    @DELETE
    suspend fun deleteClimate(
        @Query("climateId") climateId: Int
    ): Response<Int>
}
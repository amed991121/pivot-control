package com.example.controlpivot.data.remote.service

import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.local.model.SectorControl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SectorControlApiService {

    @GET(AppConstants.PIVOT_SECTOR_CONTROL_API_PATH)
    suspend fun getSectorControls(
    ): Response<List<SectorControl>>

    @POST
    suspend fun insertSectorControl(
        @Body sectorControl: SectorControl
    ): Response<Int>

    @PUT
    suspend fun updateSectorControl(
        @Body sectorControl: SectorControl
    ): Response<Int>

    @DELETE
    suspend fun deleteSectorControl(
        @Query("pivotId") sectorId: Int
    ): Response<Int>
}
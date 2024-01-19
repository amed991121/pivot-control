package com.example.controlpivot.data.remote.service

import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.data.remote.model.PivotControl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PivotControlApiService {

    @GET(AppConstants.PIVOT_CONTROL_API_PATH)
    suspend fun getPivotControls(
    ): Response<List<PivotControl>>

    @POST
    suspend fun insertPivotControl(
        @Body pivotControl: PivotControlEntity
    ): Response<Int>

    @PUT
    suspend fun updatePivotControl(
        @Body pivotControl: PivotControlEntity
    ): Response<Int>

    @DELETE
    suspend fun deletePivotControl(
        @Query("pivotId") pivotId: Int
    ): Response<Int>
}
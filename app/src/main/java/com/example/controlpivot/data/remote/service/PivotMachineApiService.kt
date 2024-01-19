package com.example.controlpivot.data.remote.service

import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.remote.model.PivotMachine
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PivotMachineApiService {

    @GET(AppConstants.PIVOT_MACHINES_API_PATH)
    suspend fun getPivotMachines(
    ): Response<List<PivotMachine>>

    @POST(AppConstants.PIVOT_MACHINES_API_PATH)
    suspend fun insertPivotMachine(
        @Body pivotMachine: PivotMachine
    ): Response<PivotMachine>

    @PUT(AppConstants.PIVOT_MACHINES_API_PATH)
    suspend fun updatePivotMachine(
        @Body pivotMachine: PivotMachine
    ): Response<Int>

    @DELETE(AppConstants.PIVOT_MACHINES_API_PATH+AppConstants.DELETE_API_PATH)
    suspend fun deletePivotMachine(
        @Path("id") id: Int
    ): Response<Int>
}
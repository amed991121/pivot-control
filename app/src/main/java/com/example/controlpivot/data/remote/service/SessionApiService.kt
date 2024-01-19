package com.example.controlpivot.data.remote.service


import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.remote.MessageBody
import com.example.controlpivot.ui.screen.login.Credentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface SessionApiService {
    @Headers("Content-Type: application/json")
    @POST(AppConstants.SESSION_API_PATH)
    suspend fun getSession(
        @Body credentials: Credentials
    ): Response<MessageBody>

    @Headers("Content-Type: application/json")
    @PUT(AppConstants.SESSION_API_PATH)
    suspend fun updateSession(
        @Body credentials: Credentials
    ): Response<Session>
}
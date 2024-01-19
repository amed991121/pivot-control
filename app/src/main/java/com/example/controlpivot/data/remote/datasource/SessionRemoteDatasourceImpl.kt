package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.remote.MessageBody
import com.example.controlpivot.data.remote.service.SessionApiService
import com.example.controlpivot.ui.screen.login.Credentials
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRemoteDatasourceImpl(
    private val sessionApiService: SessionApiService,
) : SessionRemoteDatasource {

    override suspend fun getSession(
        credentials: Credentials,
    ): Resource<Session> =
        withContext(Dispatchers.IO){
            try {
                val response = sessionApiService.getSession(
                    credentials
                )
                if (response.isSuccessful) {
                    return@withContext Resource.Success(response.body()!!.session)
                }
                Resource.Error(
                    Message.DynamicString(Gson().fromJson(
                        response.errorBody()?.charStream(),
                        MessageBody::class.java
                    ).message)
                )
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.login_error))
            }
    }

    override suspend fun updateSession(credentials: Credentials): Resource<Session> =
        withContext(Dispatchers.IO){
            try {
                val response = sessionApiService.updateSession(
                    credentials
                )
                if (response.isSuccessful) {
                    return@withContext Resource.Success(response.body()!!)
                }
                Resource.Error(Message.StringResource(R.string.update_error))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.update_error))
            }
        }
}
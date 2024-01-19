package com.example.controlpivot.data.repository

import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.local.datasource.SessionLocalDatasource
import com.example.controlpivot.data.remote.datasource.SessionRemoteDatasource
import com.example.controlpivot.ui.screen.login.Credentials
import com.example.controlpivot.utils.Resource

class SessionRepositoryImpl(
    private val remoteDatasource: SessionRemoteDatasource,
    private val localDatasource: SessionLocalDatasource,
) : SessionRepository {

    override suspend fun getSession(): Resource<Session> =
        localDatasource.getSession()

    override suspend fun fetchSession(credentials: Credentials): Resource<Int> {
        return when (val response =
            remoteDatasource.getSession(credentials)) {
            is Resource.Success -> localDatasource.saveSession(
                Session(
                    id = response.data.id,
                    name = response.data.name,
                    userName = credentials.userName,
                    password = credentials.password,
                    role = response.data.role
                )
            )

            is Resource.Error -> Resource.Error(response.message)
        }
    }

    override suspend fun updateSession(credentials: Credentials): Resource<Int> {
        return when (val response =
            remoteDatasource.updateSession(credentials)) {
            is Resource.Success -> Resource.Success(response.data.id)
            is Resource.Error -> Resource.Error(response.message)
        }
    }
}
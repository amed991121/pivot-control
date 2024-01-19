package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.ui.screen.login.Credentials
import com.example.controlpivot.utils.Resource

interface SessionRemoteDatasource {
    suspend fun getSession(
        credentials: Credentials
    ): Resource<Session>

    suspend fun updateSession(
        credentials: Credentials
    ): Resource<Session>
}
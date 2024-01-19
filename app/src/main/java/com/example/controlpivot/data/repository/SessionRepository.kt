package com.example.controlpivot.data.repository

import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.ui.screen.login.Credentials
import com.example.controlpivot.utils.Resource

interface SessionRepository {

    suspend fun getSession(): Resource<Session>

    suspend fun fetchSession(credentials: Credentials): Resource<Int>

    suspend fun updateSession(credentials: Credentials): Resource<Int>
}
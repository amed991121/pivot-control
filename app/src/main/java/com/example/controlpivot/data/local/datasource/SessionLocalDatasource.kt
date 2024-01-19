package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.utils.Resource

interface SessionLocalDatasource {
    suspend fun saveSession(session: Session): Resource<Int>
    suspend fun getSession(): Resource<Session>
}
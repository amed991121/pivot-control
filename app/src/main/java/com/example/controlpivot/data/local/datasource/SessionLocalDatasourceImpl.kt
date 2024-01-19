package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.local.DataObjectStorage
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SessionLocalDatasourceImpl(
    private val sessionStorage: DataObjectStorage<Session>,
) :
    SessionLocalDatasource {

    override suspend fun saveSession(session: Session): Resource<Int> =
        withContext(Dispatchers.IO) {
            sessionStorage.saveData(session)
        }

    override suspend fun getSession(): Resource<Session> =
        withContext(Dispatchers.IO) {
            try {
                sessionStorage.getData().first()
            } catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.get_session_error))
            }

        }
}
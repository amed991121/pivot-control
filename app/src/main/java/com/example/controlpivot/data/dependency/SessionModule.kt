package com.example.controlpivot.data.dependency

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.local.DataObjectStorage
import com.example.controlpivot.data.local.datasource.SessionLocalDatasource
import com.example.controlpivot.data.local.datasource.SessionLocalDatasourceImpl
import com.example.controlpivot.data.remote.datasource.SessionRemoteDatasource
import com.example.controlpivot.data.remote.datasource.SessionRemoteDatasourceImpl
import com.example.controlpivot.data.remote.service.SessionApiService
import com.example.controlpivot.data.repository.SessionRepository
import com.example.controlpivot.data.repository.SessionRepositoryImpl
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit


val sessionDataModule = module {
    includes(baseModule)

    single<SessionLocalDatasource> {
        SessionLocalDatasourceImpl(
            DataObjectStorage<Session>(
                get(),
                object : TypeToken<Session>() {}.type,
                androidContext().datastore,
                stringPreferencesKey((AppConstants.SESSION_PREFERENCES))
            )
        )
    }

    single<SessionApiService> {
        get<Retrofit>().create(SessionApiService::class.java)
    }

    single<SessionRemoteDatasource> {
        SessionRemoteDatasourceImpl(get())
    }

    single<SessionRepository> {
        SessionRepositoryImpl(get(), get())
    }
}
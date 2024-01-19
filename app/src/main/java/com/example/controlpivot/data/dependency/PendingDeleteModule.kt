package com.example.controlpivot.data.dependency

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.controlpivot.AppConstants
import com.example.controlpivot.data.local.DataObjectStorage
import com.example.controlpivot.data.local.datasource.MachinePendingDeleteDatasource
import com.example.controlpivot.data.local.datasource.MachinePendingDeleteDatasourceImpl
import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.data.repository.MachinePendingDeleteRepository
import com.example.controlpivot.data.repository.MachinePendingDeleteRepositoryImpl
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.datastore: DataStore<Preferences> by preferencesDataStore(AppConstants.APP_PREFERENCES)


val pendingDeleteModule = module{
    includes(baseModule)

    single<MachinePendingDeleteDatasource> {
        MachinePendingDeleteDatasourceImpl(
            DataObjectStorage<MachinePendingDelete>(
                get(),
                object : TypeToken<MachinePendingDelete>() {}.type,
                androidContext().datastore,
                stringPreferencesKey((AppConstants.PENDING_DELETE_PREFERENCES))
            )
        )
    }

    single<MachinePendingDeleteRepository> {
        MachinePendingDeleteRepositoryImpl(get())
    }

}
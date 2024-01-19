package com.example.controlpivot.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.controlpivot.AppConstants
import com.example.controlpivot.R
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class DataObjectStorage<T> constructor(
    private val gson: Gson,
    private val type: Type,
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<String>,
) {

    suspend fun saveData(data: T): Resource<Int> {
        try {
            dataStore.edit {
                val jsonString = gson.toJson(data, type)
                it[preferenceKey] = jsonString
            }
        } catch (e: Exception) {
            return Resource.Error(Message.StringResource(R.string.save_data_error))
        }
        return Resource.Success(0)
    }

    fun getData(): Flow<Resource<T>> = flow {
        dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey]?:AppConstants.EMPTY_JSON_STRING
            val elements = gson.fromJson<T>(jsonString, type)
            Log.d("TAG", elements.toString())
            elements
        }.catch {
            emit(Resource.Error(Message.StringResource(R.string.retrieve_data_error)))
        }.collect {
            if (it == null) {
                emit(Resource.Error(Message.StringResource(R.string.retrieve_data_error)))
            } else
                emit(Resource.Success(it))
        }
    }
}
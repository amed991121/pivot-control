package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.remote.MessageBody
import com.example.controlpivot.data.remote.model.PivotMachine
import com.example.controlpivot.data.remote.service.PivotMachineApiService
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PivotMachineRemoteDatasourceImpl(
    private val pivotMachineApiService: PivotMachineApiService
): PivotMachineRemoteDatasource {

    override suspend fun getPivotMachines(): Resource<List<PivotMachine>> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotMachineApiService.getPivotMachines()
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(
                    Gson().fromJson(
                        response.errorBody()?.charStream(),
                        MessageBody::class.java
                    ).message
                ))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.fetch_machines_error))
            }
        }

    override suspend fun insertPivotMachine(machine: PivotMachine): Resource<PivotMachine> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotMachineApiService.insertPivotMachine(machine)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.add_machine_error))
            }
        }

    override suspend fun updatePivotMachine(machine: PivotMachine): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotMachineApiService.updatePivotMachine(machine)
                if (response.isSuccessful)
                    return@withContext Resource.Success(response.body()!!)
                Resource.Error(Message.DynamicString(response.errorBody().toString()))
            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.update_machine_error))
            }
        }

    override suspend fun deletePivotMachine(machineId: Int): Resource<Int> =
        withContext(Dispatchers.IO) {
            try {
                val response = pivotMachineApiService.deletePivotMachine(machineId)
                if (response.isSuccessful || response.code() == 404)
                    return@withContext Resource.Success(response.body()!!)

                Resource.Error(Message.DynamicString(response.errorBody().toString()))

            }catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.delete_machine_error))
            }
        }
}
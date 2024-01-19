package com.example.controlpivot.data.repository

import com.example.controlpivot.data.local.datasource.PivotMachineLocalDatasource
import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.data.local.model.toLocal
import com.example.controlpivot.data.remote.datasource.PivotMachineRemoteDatasource
import com.example.controlpivot.data.remote.model.toNetwork
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class PivotMachineRepositoryImpl(
    private val localDatasource: PivotMachineLocalDatasource,
    private val remoteDatasource: PivotMachineRemoteDatasource,
) : PivotMachineRepository {

    override suspend fun upsertPivotMachines(machines: List<PivotMachineEntity>): Resource<Int> =
        localDatasource.upsertPivotMachines(machines)

    override suspend fun upsertPivotMachine(machine: PivotMachineEntity): Resource<Int> =
        localDatasource.addPivotMachine(machine)

    override suspend fun savePivotMachine(localId: Int): Resource<Int> {
        var machineEntity: PivotMachineEntity? = null
        when (val result = localDatasource.getPivotMachine(localId)) {
            is Resource.Error -> return Resource.Error(result.message)
            is Resource.Success -> {
                machineEntity = result.data
            }
        }
        var machineId = 0
        var isSave = true
        when (val result = remoteDatasource.insertPivotMachine(machineEntity.toNetwork())) {
            is Resource.Error -> isSave = false
            is Resource.Success -> machineId = result.data.id
        }

        return localDatasource.updatePivotMachine(
            machineEntity.copy(
                remoteId = machineId,
                isSave = isSave
            )
        )
    }

    override suspend fun registerPendingMachines() =
        synchronized(this) {
            runBlocking(Dispatchers.IO) {
                when (val result = localDatasource.getPivotMachines()) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        result.data.filter { !it.isSave }.forEach {
                            when(val response = remoteDatasource.insertPivotMachine(it.toNetwork())) {
                                is Resource.Error -> {}
                                is Resource.Success -> localDatasource.updatePivotMachine(
                                    it.copy(isSave = true))
                            }
                        }
                    }
                }
            }

        }



    override suspend fun getPivotMachine(id: Int): Resource<PivotMachineEntity> =
        localDatasource.getPivotMachine(id)

    override fun getPivotMachineAsync(id: Int): Flow<Resource<PivotMachineEntity>> =
        localDatasource.getPivotMachineAsync(id)

    override suspend fun getPivotMachineByRemoteId(remoteId: Int): Resource<PivotMachineEntity> =
        localDatasource.getPivotMachineByRemoteId(remoteId)

    override fun getAllPivotMachines(query: String): Flow<Resource<List<PivotMachineEntity>>> =
        localDatasource.getPivotMachines(query)

    override suspend fun arePendingPivotMachines(): Boolean {
        when (val result = localDatasource.getPivotMachines()) {
            is Resource.Error -> return false
            is Resource.Success -> {
                var pendingMachine = result.data.filter { !it.isSave }
                if (pendingMachine.isEmpty())
                    return true
                return false
            }
        }
    }

    override suspend fun fetchPivotMachine(): Resource<Int> {
        return when (val response = remoteDatasource.getPivotMachines()) {
            is Resource.Error -> Resource.Error(response.message)
            is Resource.Success -> {
                localDatasource.upsertPivotMachines(response.data.map { it.toLocal() })
            }
        }
    }

    override suspend fun updatePivotMachine(pivotMachine: PivotMachineEntity): Resource<Int> =
        localDatasource.updatePivotMachine(pivotMachine)

    override suspend fun deletePivotMachine(id: Int): Resource<Int> {
        return when (val result = localDatasource.getPivotMachine(id)) {
            is Resource.Error -> Resource.Error(result.message)
            is Resource.Success -> {
                localDatasource.deletePivotMachine(result.data)
            }
        }
    }
}
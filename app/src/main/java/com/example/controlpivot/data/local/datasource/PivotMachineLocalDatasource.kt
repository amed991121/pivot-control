package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PivotMachineLocalDatasource {

    suspend fun addPivotMachine(machine: PivotMachineEntity): Resource<Int>

    suspend fun getPivotMachine(id: Int): Resource<PivotMachineEntity>

    fun getPivotMachineAsync(id: Int): Flow<Resource<PivotMachineEntity>>

    suspend fun getPivotMachineByRemoteId(remoteId: Int): Resource<PivotMachineEntity>

    suspend fun getPivotMachines(): Resource<List<PivotMachineEntity>>

    fun getPivotMachines(query: String): Flow<Resource<List<PivotMachineEntity>>>

    suspend fun upsertPivotMachines(machines: List<PivotMachineEntity>): Resource<Int>

    suspend fun updatePivotMachine(machine: PivotMachineEntity): Resource<Int>

    suspend fun deletePivotMachine(machine: PivotMachineEntity): Resource<Int>
}
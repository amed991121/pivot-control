package com.example.controlpivot.data.repository

import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PivotMachineRepository {

    suspend fun upsertPivotMachines(machines: List<PivotMachineEntity>): Resource<Int>

    suspend fun upsertPivotMachine(machine: PivotMachineEntity): Resource<Int>

    suspend fun savePivotMachine(localId: Int): Resource<Int>

    suspend fun registerPendingMachines()

    suspend fun getPivotMachine(id: Int): Resource<PivotMachineEntity>

    fun getPivotMachineAsync(id: Int): Flow<Resource<PivotMachineEntity>>

    suspend fun getPivotMachineByRemoteId(remoteId: Int): Resource<PivotMachineEntity>

    fun getAllPivotMachines(query: String): Flow<Resource<List<PivotMachineEntity>>>

    suspend fun arePendingPivotMachines(): Boolean

    suspend fun fetchPivotMachine(): Resource<Int>

    suspend fun updatePivotMachine(pivotMachine: PivotMachineEntity): Resource<Int>

    suspend fun deletePivotMachine(id: Int): Resource<Int>
}
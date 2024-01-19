package com.example.controlpivot.data.remote.datasource

import com.example.controlpivot.data.remote.model.PivotMachine
import com.example.controlpivot.utils.Resource

interface PivotMachineRemoteDatasource {

    suspend fun getPivotMachines(): Resource<List<PivotMachine>>

    suspend fun insertPivotMachine(machine: PivotMachine): Resource<PivotMachine>

    suspend fun updatePivotMachine(machine: PivotMachine): Resource<Int>

    suspend fun deletePivotMachine(machineId: Int): Resource<Int>
}
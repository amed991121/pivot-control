package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.utils.Resource

interface MachinePendingDeleteDatasource {
    suspend fun savePendingDelete(machinesPending: MachinePendingDelete): Resource<Int>
    suspend fun getPendingDelete(): Resource<MachinePendingDelete>
}
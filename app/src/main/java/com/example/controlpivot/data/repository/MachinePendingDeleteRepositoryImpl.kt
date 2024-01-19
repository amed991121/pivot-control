package com.example.controlpivot.data.repository

import com.example.controlpivot.data.local.datasource.MachinePendingDeleteDatasource
import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.utils.Resource

class MachinePendingDeleteRepositoryImpl(
    private val machinePendingDeleteDatasource: MachinePendingDeleteDatasource
): MachinePendingDeleteRepository {
    override suspend fun getPendingDelete(): Resource<MachinePendingDelete> =
        machinePendingDeleteDatasource.getPendingDelete()

    override suspend fun savePendingDelete(pendingDelete: MachinePendingDelete): Resource<Int> =
        machinePendingDeleteDatasource.savePendingDelete(pendingDelete)
}
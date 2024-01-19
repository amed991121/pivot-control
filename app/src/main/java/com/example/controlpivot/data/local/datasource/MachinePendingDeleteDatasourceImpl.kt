package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.local.DataObjectStorage
import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class MachinePendingDeleteDatasourceImpl(
    private val machinePendingDeleteStorage: DataObjectStorage<MachinePendingDelete>
): MachinePendingDeleteDatasource {
    override suspend fun savePendingDelete(machinesPending: MachinePendingDelete): Resource<Int> =
        withContext(Dispatchers.IO) {
            machinePendingDeleteStorage.saveData(machinesPending)
        }

    override suspend fun getPendingDelete(): Resource<MachinePendingDelete> =
        withContext(Dispatchers.IO) {
            try {
                machinePendingDeleteStorage.getData().first()
            } catch (e: Exception) {
                Resource.Error(Message.StringResource(R.string.get_pending_delete_error))
            }

        }
}
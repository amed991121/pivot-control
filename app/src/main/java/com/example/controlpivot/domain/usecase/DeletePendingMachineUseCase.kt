package com.example.controlpivot.domain.usecase

import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.data.remote.datasource.PivotMachineRemoteDatasource
import com.example.controlpivot.data.repository.MachinePendingDeleteRepository
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DeletePendingMachineUseCase(
    private val remoteDatasource: PivotMachineRemoteDatasource,
    private val machinePendingDeleteRepository: MachinePendingDeleteRepository,
) {

    suspend operator fun invoke() =
        synchronized(this) {
            runBlocking(Dispatchers.IO) {
                machinePendingDeleteRepository.getPendingDelete().let { resource ->

                    val pendingDelete =
                        when (resource) {
                            is Resource.Error -> MachinePendingDelete()
                            is Resource.Success -> resource.data
                        }

                    val deletedList = pendingDelete.listId.toMutableList()

                    pendingDelete.listId.forEach {
                        when (remoteDatasource.deletePivotMachine(it)) {
                            is Resource.Success -> {
                                deletedList.remove(it)
                                machinePendingDeleteRepository.savePendingDelete(
                                    MachinePendingDelete(deletedList)
                                )
                            }

                            else -> {}
                        }
                    }
                }
            }
        }

}
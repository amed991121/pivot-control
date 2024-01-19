package com.example.controlpivot.data.local.datasource

import com.example.controlpivot.R
import com.example.controlpivot.data.local.database.dao.PivotMachineDao
import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class PivotMachineLocalDatasourceImpl(
    private val pivotMachineDao: PivotMachineDao
): PivotMachineLocalDatasource {

    override suspend fun addPivotMachine(machine: PivotMachineEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = pivotMachineDao.insert(machine)
            if (result > 0L)
                return@withContext Resource.Success(result.toInt())
            Resource.Error(
                Message.StringResource(R.string.add_machine_error)
            )
        }

    override suspend fun getPivotMachine(id: Int): Resource<PivotMachineEntity> =
        withContext(Dispatchers.IO) {
            val result = pivotMachineDao.get(id)
            if (result != null)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.machine_not_found)
            )
        }

    override fun getPivotMachineAsync(id: Int): Flow<Resource<PivotMachineEntity>> = flow {
        pivotMachineDao.getPivotMachineAsync(id).onEach {
            if (it == null)
                emit(Resource.Error(Message.StringResource(R.string.machine_not_found)))
            else emit(Resource.Success(it))
        }.collect()
    }

    override suspend fun getPivotMachineByRemoteId(remoteId: Int): Resource<PivotMachineEntity> =
        withContext(Dispatchers.IO) {
            val result = pivotMachineDao.getPivotMachine(remoteId)
            if (result != null)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.machine_not_found)
            )
        }

    override suspend fun getPivotMachines(): Resource<List<PivotMachineEntity>> =
        withContext(Dispatchers.IO) {
            val result = pivotMachineDao.getAllMachines()
            if (result != null)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.get_machines_error)
            )
        }

    override fun getPivotMachines(query: String): Flow<Resource<List<PivotMachineEntity>>> = flow {
        pivotMachineDao.getAll(query).onEach {
            emit(Resource.Success(it))
        }.catch {
            Resource.Error<List<PivotMachineEntity>>(
                Message.StringResource(R.string.get_machines_error)
            )
        }.collect()
    }

    override suspend fun upsertPivotMachines(machines: List<PivotMachineEntity>): Resource<Int> =
        synchronized(this) {
            runBlocking(Dispatchers.IO) {
                pivotMachineDao.getAll().forEach { machine ->
                    if (machines.find { it.remoteId == machine.id } == null)
                        pivotMachineDao.delete(machine)
                }
                val result = pivotMachineDao.upsertAll(machines)
                if (result.isEmpty() && machines.isNotEmpty())
                    return@runBlocking Resource.Error<Int>(
                        Message.StringResource(R.string.update_machines_error)
                    )
                Resource.Success(result.size)
            }
        }

    override suspend fun updatePivotMachine(machine: PivotMachineEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = pivotMachineDao.update(machine)
            if (result > 0)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.update_machine_error)
            )
        }

    override suspend fun deletePivotMachine(machine: PivotMachineEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = pivotMachineDao.delete(machine)
            if (result > 0)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.delete_machine_error)
            )
        }


}
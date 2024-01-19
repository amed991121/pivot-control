package com.example.controlpivot.data.local.datasource

import android.util.Log
import com.example.controlpivot.R
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.local.database.dao.PivotControlDao
import com.example.controlpivot.data.local.database.dao.SectorControlDao
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

class PivotControlLocalDatasourceImpl(
    private val pivotControlDao: PivotControlDao,
    private val sectorControlDao: SectorControlDao
): PivotControlLocalDatasource {

    override suspend fun addPivotControl(pivot: PivotControlEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = pivotControlDao.insert(pivot)
            if (result > 0L)
                return@withContext Resource.Success(result.toInt())
            Resource.Error(
                Message.StringResource(R.string.add_pivot_error)
            )
        }

    override suspend fun upsertSectorControl(sectorControl: SectorControl): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = sectorControlDao.insert(sectorControl)
            if (result > 0L)
                return@withContext Resource.Success(result.toInt())
            Resource.Error(
                Message.StringResource(R.string.add_sector_error)
            )
        }

    override suspend fun getPivotControl(id: Int): Resource<PivotControlEntity> =
        withContext(Dispatchers.IO) {
            val result = pivotControlDao.get(id)
            if (result != null)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.pivot_not_found)
            )
        }

    override fun getPivotControls(query: String): Flow<Resource<List<PivotControlEntity>>> = flow {
        pivotControlDao.getAll(query).onEach {
            emit(Resource.Success(it))
        }.catch {
            Resource.Error<List<PivotControlEntity>>(
                Message.StringResource(R.string.get_pivots_error)
            )
        }.collect()
    }

    override fun getSectorControls(query: String): Flow<Resource<List<SectorControl>>> = flow {
        sectorControlDao.getAllSectors(query).onEach {
            emit(Resource.Success(it))
        }.catch {
            Resource.Error<List<SectorControl>>(
                Message.StringResource(R.string.get_sectors_error)
            )
        }.collect()
    }

    override fun getSectorsForPivot(query: Int): Flow<Resource<List<SectorControl>>> = flow {
        sectorControlDao.getSectorsForPivot(query).onEach {
            emit(Resource.Success(it))
        }.catch {
            Resource.Error<List<SectorControl>>(
                Message.StringResource(R.string.get_sectors_error)
            )
        }.collect()
    }

    override suspend fun upsertPivotControls(pivots: List<PivotControlEntity>): Resource<Int> =
        synchronized(this) {
            runBlocking(Dispatchers.IO) {
                val result = pivotControlDao.upsertAll(pivots)
                if (result.isEmpty() && pivots.isNotEmpty())
                    return@runBlocking Resource.Error<Int>(
                        Message.StringResource(R.string.update_pivots_error)
                    )
                Resource.Success(result.size)
            }
        }

    override suspend fun updatePivotControl(pivot: PivotControlEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = pivotControlDao.update(pivot)
            if (result > 0)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.update_pivot_error)
            )
        }

    override suspend fun deletePivotControl(pivot: PivotControlEntity): Resource<Int> =
        withContext(Dispatchers.IO) {
            val result = pivotControlDao.delete(pivot)
            if (result > 0)
                return@withContext Resource.Success(result)
            Resource.Error(
                Message.StringResource(R.string.delete_pivot_error)
            )
        }


}
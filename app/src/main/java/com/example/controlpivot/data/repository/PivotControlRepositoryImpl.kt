package com.example.controlpivot.data.repository

import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.local.datasource.PivotControlLocalDatasource
import com.example.controlpivot.data.remote.datasource.PivotControlRemoteDatasource
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow

class PivotControlRepositoryImpl(
    private val localDatasource: PivotControlLocalDatasource,
    private val remoteDatasource: PivotControlRemoteDatasource,
) : PivotControlRepository {

    override suspend fun upsertPivotControls(pivots: List<PivotControlEntity>): Resource<Int> =
        localDatasource.upsertPivotControls(pivots)

    override suspend fun addPivotControl(pivot: PivotControlEntity): Resource<Int> {
        localDatasource.addPivotControl(pivot)
        val response = remoteDatasource.updatePivotControl(pivot)
        if (response is Resource.Error)
            return response
        return Resource.Success(0)
    }

    override suspend fun upsertSectorControl(sectorControl: SectorControl): Resource<Int> {
        localDatasource.upsertSectorControl(sectorControl)
        val response = remoteDatasource.updateSectorControl(sectorControl)
        if (response is Resource.Error)
            return response
        return Resource.Success(0)
    }

    override suspend fun getPivotControl(id: Int): Resource<PivotControlEntity> =
        localDatasource.getPivotControl(id)

    override fun getAllPivotControls(query: String): Flow<Resource<List<PivotControlEntity>>> =
        localDatasource.getPivotControls(query)

    override fun getAllSectorControls(query: String): Flow<Resource<List<SectorControl>>> =
        localDatasource.getSectorControls(query)


    override fun getSectorsForPivot(query: Int): Flow<Resource<List<SectorControl>>> =
        localDatasource.getSectorsForPivot(query)

    override suspend fun fetchPivotControl(): Resource<Int> {
        return when (val response = remoteDatasource.getPivotControls()) {
            is Resource.Error -> Resource.Error(response.message)
            is Resource.Success -> {
                response.data.forEach { control ->
                    if (control.isRunning) {
                        val result = localDatasource.getPivotControl(control.id)
                        if (result is Resource.Success) {
                            val action = remoteDatasource.updatePivotControl(result.data)
                            if (action is Resource.Error) Resource.Error<Int>(action.message)
                        }
                    } else {
                        localDatasource.addPivotControl(
                            PivotControlEntity(
                                id = control.id,
                                idPivot = control.idPivot,
                                progress = control.progress,
                                isRunning = control.isRunning,
                                stateBomb = control.stateBomb,
                                wayToPump = control.wayToPump,
                                turnSense = control.turnSense,
                                )
                        )

                    }
                    when (val result = remoteDatasource.getSectorControls()) {
                        is Resource.Error -> Resource.Error<List<SectorControl>>(result.message)
                        is Resource.Success -> {
                            result.data.forEach {
                                localDatasource.upsertSectorControl(
                                    SectorControl(
                                        id = it.id,
                                        sector_id = it.sector_id,
                                        irrigateState = it.irrigateState,
                                        dosage = it.dosage,
                                        motorVelocity = it.motorVelocity,
                                        sector_control_id = control.id
                                    )
                                )
                            }
                        }
                    }
                }
                Resource.Success(0)
            }
        }
    }

    override suspend fun updatePivotControl(pivot: PivotControlEntity): Resource<Int> =
        remoteDatasource.updatePivotControl(pivot)

    override suspend fun updateSectorControl(sectorControl: SectorControl): Resource<Int> =
        remoteDatasource.updateSectorControl(sectorControl)

    override suspend fun deletePivotControl(id: Int): Resource<Int> =
        remoteDatasource.deletePivotControl(id)
}
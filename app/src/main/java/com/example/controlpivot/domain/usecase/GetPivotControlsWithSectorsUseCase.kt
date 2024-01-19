package com.example.controlpivot.domain.usecase

import android.util.Log
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.repository.PivotControlRepository
import com.example.controlpivot.ui.model.PivotControlsWithSectors
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach


class GetPivotControlsWithSectorsUseCase(
    private val controlRepository: PivotControlRepository,
) {

    operator fun invoke(query: String): Flow<Resource<List<PivotControlsWithSectors>>> = flow {
        controlRepository.getAllPivotControls(query)
            .combine(controlRepository.getAllSectorControls("")) { result, resource ->
                when (result) {
                    is Resource.Error -> {
                        emit(Resource.Error(result.message))
                        return@combine
                    }

                    is Resource.Success -> {
                        var sectorList = listOf<SectorControl>()

                        when (resource) {
                            is Resource.Error -> {
                                emit(Resource.Error(resource.message))
                                return@combine
                            }

                            is Resource.Success -> {
                                sectorList = resource.data
                                Log.d("GET", resource.data.toString())
                            }
                        }
                        emit(
                            Resource.Success(
                                result.data.map { pivotControl ->

                                    PivotControlsWithSectors(
                                        id = pivotControl.id,
                                        idPivot = pivotControl.idPivot,
                                        progress = pivotControl.progress,
                                        isRunning = pivotControl.isRunning,
                                        stateBomb = pivotControl.stateBomb,
                                        wayToPump = pivotControl.wayToPump,
                                        turnSense = pivotControl.turnSense,
                                        sectorList = sectorList.filter { it.sector_control_id == pivotControl.id }
                                    )

                                }
                            ))
                    }
                }
            }.collect()
    }

}
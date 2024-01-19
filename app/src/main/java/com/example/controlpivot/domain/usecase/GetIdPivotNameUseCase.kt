package com.example.controlpivot.domain.usecase

import com.example.controlpivot.data.repository.PivotMachineRepository
import com.example.controlpivot.ui.model.IdPivotModel
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GetIdPivotNameUseCase(
    private val pivotMachineRepository: PivotMachineRepository,
) {

    operator fun invoke(query: String): Flow<Resource<List<IdPivotModel>>> = flow {
        pivotMachineRepository.getAllPivotMachines(query).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    emit(Resource.Error(result.message))
                    return@onEach
                }

                is Resource.Success -> {
                    emit(Resource.Success(
                        result.data.filter { it.remoteId != Int.MAX_VALUE }
                            .map { machine ->
                                IdPivotModel(
                                    idPivot = machine.remoteId,
                                    pivotName = machine.name
                                )
                            }
                    ))
                }
            }
        }.collect()
    }

}

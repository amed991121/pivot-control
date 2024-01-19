package com.example.controlpivot.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlpivot.ConnectivityObserver
import com.example.controlpivot.NetworkConnectivityObserver
import com.example.controlpivot.R
import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.local.model.MachinePendingDelete
import com.example.controlpivot.data.local.model.PivotMachineEntity
import com.example.controlpivot.data.remote.datasource.PivotMachineRemoteDatasource
import com.example.controlpivot.data.repository.MachinePendingDeleteRepository
import com.example.controlpivot.data.repository.PivotMachineRepository
import com.example.controlpivot.data.repository.SessionRepository
import com.example.controlpivot.domain.usecase.DeletePendingMachineUseCase
import com.example.controlpivot.ui.screen.createpivot.PivotMachineEvent
import com.example.controlpivot.ui.screen.createpivot.PivotMachineState
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PivotMachineViewModel(
    private val connectivityObserver: NetworkConnectivityObserver,
    private val pivotMachineRepository: PivotMachineRepository,
    private val sessionRepository: SessionRepository,
    private val pendingDeleteUseCase: DeletePendingMachineUseCase,
    private val machinePendingDeleteRepository: MachinePendingDeleteRepository,
    private val pivotMachineRemoteDatasource: PivotMachineRemoteDatasource,
) : ViewModel() {

    private var session: Session? = null
    private var _networkStatus = ConnectivityObserver.Status.Available

    private var getCurrentMachineJob: Job? = null
    private var deleteMachineJob: Job? = null
    private var getMachinesByNameJob: Job? = null
    private var registerMachineJob: Job? = null
    private var getSessionJob: Job? = null
    private var refreshDataJob: Job? = null
    private var networkObserverJob: Job? = null

    private val _state = MutableStateFlow(PivotMachineState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    sealed class Event() {
        class ShowMessage(val message: Message) : Event()
        data object PivotMachineCreated : Event()
    }

    init {
        loadSession()
        getPivotMachineByName()
        observeNetworkChange()
        refreshData()
    }

    fun onEvent(event: PivotMachineEvent) {
        when (event) {
            is PivotMachineEvent.DeleteMachine -> {
                deleteMachine(event.machineId)
            }

            is PivotMachineEvent.ResetDataMachine -> {
                _state.update { it.copy(currentMachine = PivotMachineEntity()) }
            }

            is PivotMachineEvent.SelectMachine -> {
                getCurrentMachine(event.id)
            }

            is PivotMachineEvent.CreateMachine -> {
                registerPivotMachine(event.machine)
            }

            is PivotMachineEvent.SearchMachine -> {
                getPivotMachineByName(event.query)
            }
        }
    }

    fun refreshData() {
        refreshDataJob?.cancel()
        refreshDataJob = viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            if (isNetworkAvailable()) {
                pivotMachineRepository.registerPendingMachines()
                pendingDeleteUseCase()
                if (pivotMachineRepository.arePendingPivotMachines()) reloadPivotMachines()
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun reloadPivotMachines() {
        val result = pivotMachineRepository.fetchPivotMachine()
        if (result is Resource.Error) _event.emit(Event.ShowMessage(result.message))
    }

    private fun loadSession() {
        getSessionJob?.cancel()
        getSessionJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = sessionRepository.getSession()) {
                is Resource.Error -> {
                    _event.emit(Event.ShowMessage(result.message))
                }

                is Resource.Success -> session = result.data
            }
        }
    }

    private fun registerPivotMachine(machine: PivotMachineEntity) {
        registerMachineJob?.cancel()
        registerMachineJob = viewModelScope.launch(Dispatchers.IO) {
            if (machine.name == "" || machine.location == ""
                || machine.endowment == 0.0 || machine.flow == 0.0
                || machine.pressure == 0.0 || machine.area == 0.0
                || machine.length == 0.0 || machine.power == 0.0
                || machine.efficiency == 0.0 || machine.speed == 0.0
            ) {
                _event.emit(
                    Event.ShowMessage(Message.StringResource(R.string.values_are_required)))
                return@launch
            } else {
                when (val result = pivotMachineRepository.upsertPivotMachine(machine)) {
                    is Resource.Error -> _event.emit(Event.ShowMessage(result.message))
                    is Resource.Success -> {
                        _event.emit(Event.PivotMachineCreated)
                        if (isNetworkAvailable()) {
                            when (val response =
                                pivotMachineRepository.savePivotMachine(result.data)) {
                                is Resource.Error -> _event.emit(Event.ShowMessage(response.message))
                                is Resource.Success -> {}
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun isNetworkAvailable(): Boolean {
        if (_networkStatus != ConnectivityObserver.Status.Available) {
            _event.emit(
                Event.ShowMessage(Message.StringResource(R.string.internet_error)))
            return false
        }
        return true
    }

    private fun getPivotMachineByName(query: String = "") {
        getMachinesByNameJob?.cancel()
        getMachinesByNameJob = viewModelScope.launch(Dispatchers.IO) {
            pivotMachineRepository.getAllPivotMachines(query).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _event.emit(Event.ShowMessage(result.message))
                    is Resource.Success -> _state.update { it.copy(machines = result.data) }
                }
            }
        }
    }

    private fun getCurrentMachine(id: Int) {
        getCurrentMachineJob?.cancel()
        getCurrentMachineJob = viewModelScope.launch(Dispatchers.IO) {
            pivotMachineRepository.getPivotMachineAsync(id).onEach { result ->
                when (result) {
                    is Resource.Error -> _event.emit(Event.ShowMessage(result.message))
                    is Resource.Success -> _state.update { it.copy(currentMachine = result.data) }

                }
            }.collect()
        }
    }

    private fun deleteMachine(machineId: Int) {
        deleteMachineJob?.cancel()
        deleteMachineJob = viewModelScope.launch(Dispatchers.IO) {
            when (val response = pivotMachineRepository.getPivotMachine(machineId)) {
                is Resource.Error -> _event.emit(Event.ShowMessage(response.message))
                is Resource.Success -> {
                    val result = pivotMachineRepository.deletePivotMachine(machineId)
                    if (result is Resource.Error) _event.emit(Event.ShowMessage(result.message))
                    else {

                        if (response.data.isSave) {

                            val machinePendingDelete =
                                when (val result =
                                    machinePendingDeleteRepository.getPendingDelete()) {
                                    is Resource.Error -> MachinePendingDelete()
                                    is Resource.Success -> result.data
                                }

                            if (isNetworkAvailable()) {
                                pivotMachineRemoteDatasource.deletePivotMachine(response.data.remoteId)
                            } else {
                                val listId = machinePendingDelete.listId.toMutableList()
                                listId.add(response.data.remoteId)
                                when (val resultDelete =
                                    machinePendingDeleteRepository.savePendingDelete(
                                        MachinePendingDelete(listId)
                                    )) {
                                    is Resource.Error -> _event.emit(
                                        Event.ShowMessage(resultDelete.message)
                                    )

                                    is Resource.Success -> {

                                    }
                                }
                            }
                        }
                        _event.emit(
                            Event.ShowMessage(
                                Message.StringResource(R.string.machine_deleted)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeNetworkChange() {
        networkObserverJob?.cancel()
        networkObserverJob = viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest { status ->
                _networkStatus = status
            }
        }

    }

}
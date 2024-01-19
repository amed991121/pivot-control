package com.example.controlpivot.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlpivot.ConnectivityObserver
import com.example.controlpivot.NetworkConnectivityObserver
import com.example.controlpivot.R
import com.example.controlpivot.data.local.model.PivotControlEntity
import com.example.controlpivot.data.local.model.SectorControl
import com.example.controlpivot.data.repository.PivotControlRepository
import com.example.controlpivot.domain.usecase.GetIdPivotNameUseCase
import com.example.controlpivot.domain.usecase.GetPivotControlsWithSectorsUseCase
import com.example.controlpivot.ui.screen.pivotcontrol.ControlEvent
import com.example.controlpivot.ui.screen.pivotcontrol.ControlState
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PivotControlViewModel(
    private val connectivityObserver: NetworkConnectivityObserver,
    private val controlRepository: PivotControlRepository,
    private val getIdPivotNameUseCase: GetIdPivotNameUseCase,
    private val getPivotControlsWithSectorsUseCase: GetPivotControlsWithSectorsUseCase,
) : ViewModel() {

    private var _networkStatus = ConnectivityObserver.Status.Available
    private var saveControlsJob: Job? = null
    private var saveSectorsJob: Job? = null
    private var networkObserverJob: Job? = null
    private var selectByIdPivotJob: Job? = null
    private var getIdPivotNameListJob: Job? = null
    private var getPivotControls: Job? = null
    private var showMessageJob: Job? = null
    private var refreshDataJob: Job? = null

    private val _state = MutableStateFlow(ControlState())
    val state = _state.asStateFlow()

    private val _message = MutableSharedFlow<Message>()
    val message = _message.asSharedFlow()

    init {
        observeNetworkChange()
        getIdPivotNameList()
        getPivotControls()
        refreshData()
    }

    fun onEvent(event: ControlEvent) {
        when (event) {
            is ControlEvent.SaveControls -> saveControls(event.controls)
            is ControlEvent.SaveSectors -> saveSectors(event.sectors)
            is ControlEvent.SelectControlByIdPivot -> selectByIdPivot(event.id)
            is ControlEvent.ShowMessage -> showMessage(event.string)
        }
    }

    private fun refreshData() {
        refreshDataJob?.cancel()
        refreshDataJob = viewModelScope.launch(Dispatchers.IO) {
            if (isNetworkAvailable()) {
                while (true) {
                    getPivotControls()
                    delay(1000)
                }
            }
        }
    }

    private fun showMessage(string: Int) {
        showMessageJob?.cancel()
        showMessageJob = viewModelScope.launch(Dispatchers.IO) {
            _message.emit(Message.StringResource(string))
        }
    }

    private fun getPivotControls() {
        getPivotControls?.cancel()
        getPivotControls = viewModelScope.launch(Dispatchers.IO) {
            val resource = controlRepository.fetchPivotControl()
            if (resource is Resource.Error) {
                _message.emit(resource.message)
            }
        }
    }

    private fun getIdPivotNameList(query: String = "") {
        getIdPivotNameListJob?.cancel()
        getIdPivotNameListJob = viewModelScope.launch(Dispatchers.IO) {
            getIdPivotNameUseCase(query).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _message.emit(result.message)
                    is Resource.Success -> _state.update { it.copy(idPivotList = result.data) }
                }
            }
        }
    }


    private fun selectByIdPivot(id: Int, query: String = "") {
        selectByIdPivotJob?.cancel()
        selectByIdPivotJob = viewModelScope.launch(Dispatchers.IO) {
            getPivotControlsWithSectorsUseCase(query).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _message.emit(result.message)
                    is Resource.Success -> {
                        _state.update { controlState ->
                            controlState.copy(controls = result.data.find { it.id == id }
                                ?: state.value.controls)
                        }
                        Log.d("CONTROLS", _state.value.toString())
                    }
                }
            }

        }
    }

    private fun saveControls(controls: PivotControlEntity) {
        saveControlsJob?.cancel()
        saveControlsJob = viewModelScope.launch(Dispatchers.IO) {
            val result = controlRepository.addPivotControl(controls)
            _state.update {
                it.copy(
                    controls = it.controls.copy(
                        id = controls.id,
                        idPivot = controls.idPivot,
                        progress = controls.progress,
                        isRunning = controls.isRunning,
                        stateBomb = controls.stateBomb,
                        wayToPump = controls.wayToPump,
                        turnSense = controls.turnSense,
                    )
                )
            }
            if (result is Resource.Error) {
                _message.emit(result.message)
            }
        }
    }

    private fun saveSectors(sectors: SectorControl) {
        saveSectorsJob?.cancel()
        saveSectorsJob = viewModelScope.launch(Dispatchers.IO) {
            val result = controlRepository.upsertSectorControl(sectors)
            selectByIdPivot(sectors.sector_control_id)
            if (result is Resource.Error) _message.emit(result.message)
        }
    }

    private suspend fun isNetworkAvailable(): Boolean {
        if (_networkStatus != ConnectivityObserver.Status.Available) {
            _message.emit(Message.StringResource(R.string.internet_error))
            _state.update { it.copy(networkStatus = false) }
            return false
        }
        _state.update { it.copy(networkStatus = true) }
        return true
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
package com.example.controlpivot.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlpivot.ConnectivityObserver
import com.example.controlpivot.NetworkConnectivityObserver
import com.example.controlpivot.R
import com.example.controlpivot.data.repository.ClimateRepository
import com.example.controlpivot.domain.usecase.GetIdPivotNameUseCase
import com.example.controlpivot.ui.screen.climate.ClimateEvent
import com.example.controlpivot.ui.screen.climate.ClimateState
import com.example.controlpivot.utils.Message
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClimateViewModel(
    private val climateRepository: ClimateRepository,
    private val connectivityObserver: NetworkConnectivityObserver,
    private val getIdPivotNameUseCase: GetIdPivotNameUseCase,
) : ViewModel() {

    private var _networkStatus = ConnectivityObserver.Status.Available

    private var networkObserverJob: Job? = null
    private var selectByIdPivotJob: Job? = null
    private var getClimatesJob: Job? = null
    private var getIdPivotNameListJob: Job? = null
    private var refreshDataJob: Job? = null

    private val _state = MutableStateFlow(ClimateState())
    val state = _state.asStateFlow()

    private val _message = MutableSharedFlow<Message>()
    val message = _message.asSharedFlow()

    init {
        getClimates()
        getIdPivotNameList()
        observeNetworkChange()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: ClimateEvent) {
        when (event) {
            is ClimateEvent.SelectClimateByIdPivot -> selectByIdPivot(event.id)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectByIdPivot(id: Int) {
        selectByIdPivotJob?.cancel()
        selectByIdPivotJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = climateRepository.getClimatesById(id)) {
                is Resource.Error -> {}
                is Resource.Success -> _state.update { climateState ->

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val listTime = result.data.map { LocalDateTime.parse(it.timestamp, formatter) }
                    val timeMostRecently = listTime.maxByOrNull { it }

                    val climate = result.data.find { it.timestamp == timeMostRecently!!.format(formatter) }

                    climateState.copy(currentClimate = climate) }
            }
            Log.d("OneClimate", _state.value.currentClimate.toString())
        }
    }

    fun refreshData() {
        refreshDataJob?.cancel()
        refreshDataJob = viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            if (isNetworkAvailable()) {
                getClimates()
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun getClimates() {
        getClimatesJob?.cancel()
        getClimatesJob = viewModelScope.launch(Dispatchers.IO) {
            if (climateRepository.fetchClimate() is Resource.Success) {
                climateRepository.getAllClimates("").collectLatest { result ->
                    when (result) {
                        is Resource.Error -> _message.emit(result.message)
                        is Resource.Success -> {
                            _state.update { it.copy(climates = result.data) }
                        }
                    }
                }
            }
        }
    }

    private suspend fun isNetworkAvailable(): Boolean {
        if (_networkStatus != ConnectivityObserver.Status.Available) {
            _message.emit(Message.StringResource(R.string.internet_error))
            return false
        }
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
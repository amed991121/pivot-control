package com.example.controlpivot.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlpivot.ConnectivityObserver
import com.example.controlpivot.NetworkConnectivityObserver
import com.example.controlpivot.R
import com.example.controlpivot.data.common.model.Session
import com.example.controlpivot.data.repository.SessionRepository
import com.example.controlpivot.ui.screen.login.Credentials
import com.example.controlpivot.ui.screen.login.LoginEvent
import com.example.controlpivot.ui.screen.login.LoginState
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

class LoginViewModel(
    private val sessionRepository: SessionRepository,
    private val connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private var loginJob: Job? = null
    private var networkObserverJob: Job? = null
    private var changeCredentialsJob: Job? = null
    private var getSessionJob: Job? = null

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _message = MutableSharedFlow<Message>()
    val message = _message.asSharedFlow()

    private val _isLogged = MutableLiveData(false)
    val isLogged: LiveData<Boolean> = _isLogged

    private var _networkStatus = ConnectivityObserver.Status.Available
    private var session: Session? = null

    init {
        loadSession()
        observeNetworkChange()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.credentials)
            is LoginEvent.ChangeCredentials -> changeCredentials(event.credentials)
        }
    }

    private fun changeCredentials(credentials: Credentials) {
        changeCredentialsJob?.cancel()
        changeCredentialsJob = viewModelScope.launch(Dispatchers.IO) {
            credentials.validate().let { result ->
                if (result is Resource.Error) {
                    _message.emit(result.message)
                    return@launch
                }
            }

            if (!isNetworkAvalible()) return@launch
            _state.update {
                it.copy(isLoading = true)
            }

            when (val result = sessionRepository.updateSession(
                credentials
            )) {
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _message.emit(result.message)
                }

                is Resource.Success -> {
                    _isLogged.postValue(true)
                    _message.emit(Message.StringResource(R.string.update_success))
                }
            }
        }
    }

    fun login(credentials: Credentials) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch(Dispatchers.IO) {
            credentials.validate().let { result ->
                if (result is Resource.Error) {
                    _message.emit(result.message)
                    return@launch
                }
            }

            if (!isNetworkAvalible()) return@launch
            _state.update {
                it.copy(isLoading = true)
            }

            when (val result = sessionRepository.fetchSession(
                credentials
            )) {
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _message.emit(result.message)
                }

                is Resource.Success -> {
                    _isLogged.postValue(true)
                }
            }
        }
    }

    private suspend fun isNetworkAvalible(): Boolean {
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

    fun loadSession() {
        getSessionJob?.cancel()
        getSessionJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = sessionRepository.getSession()) {
                is Resource.Error -> {
                    _message.emit(result.message)
                }

                is Resource.Success -> {
                    Log.d("LOG", result.data.toString())
                    _state.update { it.copy(session = result.data) }}
            }
        }
    }
}
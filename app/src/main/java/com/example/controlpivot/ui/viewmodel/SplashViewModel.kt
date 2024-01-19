package com.example.controlpivot.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlpivot.data.repository.SessionRepository
import com.example.controlpivot.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashViewModel(private val sessionRepository: SessionRepository): ViewModel() {
    private val _isLogged = MutableLiveData<Boolean?>(null)
    val isLogged: LiveData<Boolean?> = _isLogged
    var job: Job? = null
    fun checkLogin(){
        job?.cancel()
        job = viewModelScope.launch{
            when(val result = sessionRepository.getSession()){
                is Resource.Error -> _isLogged.postValue(false)
                is Resource.Success -> { _isLogged.postValue(true) }
            }
        }
    }

}
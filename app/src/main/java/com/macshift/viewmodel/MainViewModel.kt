// File: MainViewModel.kt 
package com.macshift.viewmodel

import androidx.lifecycle.LiveData 
import androidx.lifecycle.MutableLiveData 
import androidx.lifecycle.ViewModel 
import androidx.lifecycle.viewModelScope 
import com.macshift.repository.MacRepository 
import kotlinx.coroutines.Dispatchers 
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() { private val repo = MacRepository()

private val _isRooted = MutableLiveData<Boolean>()
val isRooted: LiveData<Boolean> = _isRooted

private val _currentMac = MutableLiveData<String>()
val currentMac: LiveData<String> = _currentMac

private val _operationResult = MutableLiveData<String>()
val operationResult: LiveData<String> = _operationResult

private val _errorMessage = MutableLiveData<String>()
val errorMessage: LiveData<String> = _errorMessage

fun initialize() {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            _isRooted.postValue(repo.checkRoot())
            _currentMac.postValue(repo.getCurrentMac())
        } catch (e: Exception) {
            _errorMessage.postValue(e.message)
        }
    }
}

fun spoofRandomMac() {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            val newMac = repo.spoofRandomMac()
            _currentMac.postValue(newMac)
            _operationResult.postValue("Random MAC applied: $newMac")
        } catch (e: Exception) {
            _errorMessage.postValue(e.message)
        }
    }
}

fun spoofCustomMac(mac: String) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            repo.spoofCustomMac(mac)
            _currentMac.postValue(repo.getCurrentMac())
            _operationResult.postValue("Custom MAC applied: $mac")
        } catch (e: Exception) {
            _errorMessage.postValue(e.message)
        }
    }
}

fun restoreOriginalMac() {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            repo.restoreOriginalMac()
            _currentMac.postValue(repo.getCurrentMac())
            _operationResult.postValue("Original MAC restored")
        } catch (e: Exception) {
            _errorMessage.postValue(e.message)
        }
    }
}

fun setAutoSpoofOnBoot(enabled: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
        repo.setAutoSpoofOnBoot(enabled)
        _operationResult.postValue(
            if (enabled) "Auto-spoof on boot enabled"
            else "Auto-spoof on boot disabled"
        )
    }
}

}


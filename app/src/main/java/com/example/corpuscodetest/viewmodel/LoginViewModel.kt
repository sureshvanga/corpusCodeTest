package com.example.corpuscodetest.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _showOtp = MutableLiveData(false)
    val showOtp: LiveData<Boolean> = _showOtp

    fun onMobileChanged(mobile: String) {
        _showOtp.value = mobile.length == 10
    }
}
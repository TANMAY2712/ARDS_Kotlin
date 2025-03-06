package com.ards.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ards.remote.apimodel.GenrateOTPResponse

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    fun sendOtp(phone: String): LiveData<Result<GenrateOTPResponse>> {
        return repository.sendOtp(phone)
    }
}

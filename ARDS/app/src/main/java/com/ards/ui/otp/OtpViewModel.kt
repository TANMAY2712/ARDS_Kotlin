package com.ards.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ards.remote.apimodel.VerifyOtpResponse


class OtpViewModel : ViewModel() {
    private val repository = OtpRepository()

    fun verifyOTP(phone: String,otp: Int): LiveData<Result<VerifyOtpResponse>> {
        return repository.verifyOTP(phone,otp)
    }
}

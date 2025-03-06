package com.ards.ui.otp

import com.ards.remote.service.ArdsService
import com.wus.loan.remote.ApiFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ards.remote.apimodel.VerifyOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpRepository {
    private val apiService: ArdsService = ApiFactory.RetrofitClient.createService(ArdsService::class.java)


    fun verifyOTP(phone: String,otp: String): LiveData<Result<VerifyOtpResponse>> {
        val liveData = MutableLiveData<Result<VerifyOtpResponse>>()
        val call = apiService.verifyOTP(OtpRequest(phone,"91","","AR-AUG-ARST-BIZBR-2019OLLY",""))

        call.enqueue(object : Callback<VerifyOtpResponse> {
            override fun onResponse(call: Call<VerifyOtpResponse>, response: Response<VerifyOtpResponse>) {
                if (response.isSuccessful) {
                    liveData.value = Result.success(response.body()!!)
                } else {
                    liveData.value = Result.failure(Exception("Error: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {
                liveData.value = Result.failure(t)
            }
        })

        return liveData
    }
}

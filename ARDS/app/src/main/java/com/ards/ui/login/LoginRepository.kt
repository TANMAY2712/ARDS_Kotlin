package com.ards.ui.login

import com.ards.remote.service.ArdsService
import com.wus.loan.remote.ApiFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ards.remote.apimodel.GenrateOTPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    private val apiService: ArdsService = ApiFactory.RetrofitClient.createService(ArdsService::class.java)


    fun sendOtp(phone: String): LiveData<Result<GenrateOTPResponse>> {
        val liveData = MutableLiveData<Result<GenrateOTPResponse>>()
        val call = apiService.sendOtp(LoginRequest(phone,"91","tanmay2712d@gmail.com","AR-AUG-ARST-BIZBR-2019OLLY"))

        call.enqueue(object : Callback<GenrateOTPResponse> {
            override fun onResponse(call: Call<GenrateOTPResponse>, response: Response<GenrateOTPResponse>) {
                if (response.isSuccessful) {
                    liveData.value = Result.success(response.body()!!)
                } else {
                    liveData.value = Result.failure(Exception("Error: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<GenrateOTPResponse>, t: Throwable) {
                liveData.value = Result.failure(t)
            }
        })

        return liveData
    }
}

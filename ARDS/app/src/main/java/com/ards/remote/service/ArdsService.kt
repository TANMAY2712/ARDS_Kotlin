package com.ards.remote.service

import com.ards.remote.apimodel.GenrateOTPRequest
import com.ards.utils.Constant
import retrofit2.Call
import com.ards.remote.apimodel.VerifyOtpResponse
import com.ards.remote.apimodel.GenrateOTPResponse
import com.ards.remote.apimodel.MasterDataRequest
import com.ards.remote.apimodel.MasterDataResponse
import com.ards.remote.apimodel.NotificationFaultListRequest
import com.ards.remote.apimodel.NotificationFaultListResponse
import com.ards.remote.apimodel.NotificationListRequest
import com.ards.remote.apimodel.NotificationListResponse
import com.ards.remote.apimodel.SignInRequest
import com.ards.remote.apimodel.SignInResponse
import com.ards.remote.apimodel.VerifyOtpRequest
import com.ards.remote.apimodel.VideoByCategoryRequest
import com.ards.remote.apimodel.VideoByCategoryResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ArdsService {

    @POST(Constant.ApiEndPoint.VideoByCategory)
    fun getVideoByCategory(@Body request: VideoByCategoryRequest): Call<VideoByCategoryResponse>

    @POST(Constant.ApiEndPoint.GenrateOTP)
    fun sendOtp(@Body request: GenrateOTPRequest): Call<GenrateOTPResponse>

    @POST(Constant.ApiEndPoint.VerifyOTP)
    fun verifyOTP(@Body request: VerifyOtpRequest): Call<VerifyOtpResponse>

    @POST(Constant.ApiEndPoint.NotificationFault)
    fun getNotificationFaultList(@Body request: NotificationFaultListRequest): Call<NotificationFaultListResponse>

    @POST(Constant.ApiEndPoint.NotificationList)
    fun getAllNotification(@Body request: NotificationListRequest): Call<NotificationListResponse>

    @POST(Constant.ApiEndPoint.SignIn)
    fun signIn(@Body request: SignInRequest): Call<SignInResponse>

    @POST(Constant.ApiEndPoint.ChartByFaultType)
    fun chartByFaultType(@Body request: SignInRequest): Call<SignInResponse>

    @POST(Constant.ApiEndPoint.MasterData)
    fun masterData(@Body request: MasterDataRequest): Call<MasterDataResponse>

    @POST(Constant.ApiEndPoint.UserProfile)
    fun updateUserProfile(@Body request: MasterDataRequest): Call<MasterDataResponse>

}
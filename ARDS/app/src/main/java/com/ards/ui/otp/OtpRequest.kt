package com.ards.ui.otp

data class OtpRequest(
    val Username: String,
    val CountryCode: String,
    val OTP: String,
    val APIKey: String,
    val RefferedCode: String
)

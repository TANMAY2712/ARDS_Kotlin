package com.ards.ui.otp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ards.MainActivity
import com.ards.databinding.ActivityOtpBinding
import com.ards.sharedpreference.PreferenceHelper
import com.ards.utils.Constant
import com.ards.utils.SmsOTPBRoadCast
import com.google.android.gms.auth.api.phone.SmsRetriever

class OtpActivity : AppCompatActivity() {
    private val TAG = "OtpActivity"
    lateinit var smsBroadcastReceiver: SmsOTPBRoadCast
    private lateinit var binding: ActivityOtpBinding
    private lateinit var mobileNumber: String
    private val verifyViewModel: OtpViewModel by viewModels()

    companion object {
        const val REQ_USER_CONSENT = 33
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Start user consent to read the OTP SMS
        startSmsUserConsent()

        mobileNumber = intent.getStringExtra("userMobileNumber").toString()
        binding.tvPhoneNumber.text = mobileNumber

        verifyViewModel.isLoading.observe(this) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        binding.btnVerify.setOnClickListener {
            val otp = binding.pinView.text.toString()

            if (otp.length == 6) {
                verifyOtp(otp.toInt())
            } else {
                Toast.makeText(this, "Enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // for otp
    override fun onStart() {
        super.onStart()
        registerToSmsBroadcastReceiver()
        Log.d(TAG, "reg")
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerToSmsBroadcastReceiver() {
        smsBroadcastReceiver = SmsOTPBRoadCast().also {
            it.smsBroadcastReceiverListener =
                object : SmsOTPBRoadCast.SmsBroadcastReceiverListener {
                    override fun onSuccess(intent: Intent?) {
                        intent?.let { context -> startActivityForResult(context, REQ_USER_CONSENT) }
                    }

                    override fun onFailure() {
                    }
                }
        }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            registerReceiver(smsBroadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(smsBroadcastReceiver, intentFilter)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
    }

    private fun fetchVerificationCode(message: String): String {
        return Regex("(\\d{6})").find(message)?.value ?: ""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_USER_CONSENT -> {
                if ((resultCode == Activity.RESULT_OK) && (data != null)) {
                    //That gives all message to us. We need to get the code from inside with regex
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    val code = message?.let { fetchVerificationCode(it) }
                    val otpInParts = code!!.toList()
                    fillOtpInBoxes(otpInParts)
                }
            }
        }
    }

    private fun fillOtpInBoxes(otpInParts: List<Char>) {
        var otp = ""
        for (char in otpInParts) {
            otp += char
        }
        Constant.showShortToast(otp,this)

        binding.pinView.setText(otp)
    }

    private fun startSmsUserConsent() {
        SmsRetriever.getClient(this).also {
            //We can add user phone number or leave it blank
            it.startSmsUserConsent(null)
                .addOnSuccessListener {
                    Log.d(TAG, "LISTENING_SUCCESS")
                }
                .addOnFailureListener {
                    Log.d(TAG, "LISTENING_FAILURE")
                }
        }
    }

    private fun verifyOtp(otp: Int) {
        verifyViewModel.verifyOTP(mobileNumber, otp).observe(this) { result ->

            result.onSuccess {
                PreferenceHelper.putBoolean("isLoggedIn", true)
                Toast.makeText(this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            result.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

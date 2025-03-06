package com.ards.ui.otp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ards.MainActivity
import com.ards.R
import com.ards.databinding.ActivityLoginBinding
import com.ards.databinding.ActivityOtpBinding
import com.ards.ui.login.LoginViewModel

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private val verifyViewModel: OtpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnVerify.setOnClickListener {
            val otp = binding.pinView.text.toString()
              // Concatenating all OTP digits

            if(otp.length==6){
                verifOtp(otp.toString())
            }else{
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun verifOtp(otp: String) {
        verifyViewModel.verifyOTP("8299112349",otp).observe(this) { result ->
            result.onSuccess { response ->
                Toast.makeText(this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                /*if (response.SuccessMessage) {
                    Toast.makeText(this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, OtpActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, response.SuccessMessage, Toast.LENGTH_SHORT).show()
                }*/
            }

            result.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.ards.ui.login
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ards.databinding.ActivityLoginBinding  // Import generated binding class
import com.ards.ui.otp.OtpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding  // Declare binding variable
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendOtp.setOnClickListener {
            sendOtp(binding.etPhoneNumber.text.toString())
        }
    }

    private fun sendOtp(phone: String) {
        loginViewModel.sendOtp(phone).observe(this) { result ->
            result.onSuccess { response ->
                Toast.makeText(this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("userMobileNumber", binding.etPhoneNumber.text.toString())
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

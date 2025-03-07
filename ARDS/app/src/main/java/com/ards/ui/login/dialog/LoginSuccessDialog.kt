package com.ards.ui.login.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ards.R
import com.ards.databinding.LoginSuccessfulDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
/**
 * This will create a success dialog with the specified params
 * @param callback - to handle the continue button click
 * @param title - the title of the dialog
 * @param message - the message of the dialog
 * @param image - success image or failed image to show in dialog
 */
class LoginSuccessDialog(
    private val callback: Callback,
    private val title: String,
    private val message: String,
    private val image: Int,
    private val buttonText: String
) : BottomSheetDialogFragment() {

    private lateinit var binding : LoginSuccessfulDialogBinding

    companion object {
        val TAG = "LoginSuccessDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginSuccessfulDialogBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.loginSuccessfulDialogSuccessImage.setImageResource(image)
        binding.loginSuccessfulDialogTvTitle.text = title
        binding.loginSuccessfulDialogTvDescription.text = message
        binding.loginSuccessfulDialogBtnContinue.text = buttonText
        binding.loginSuccessfulDialogBtnContinue.setOnClickListener {
            callback.onContinueClick()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    interface Callback {
        fun onContinueClick()
    }
}
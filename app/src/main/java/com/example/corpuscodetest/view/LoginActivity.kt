package com.example.corpuscodetest.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.corpuscodetest.R
import com.example.corpuscodetest.databinding.ActivityLoginBinding
import com.example.corpuscodetest.others.DataStoreManager
import com.example.corpuscodetest.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.otpLayout.visibility = View.GONE

        binding.mobileInput.doAfterTextChanged {
            viewModel.onMobileChanged(it.toString())
        }

        viewModel.showOtp.observe(this) { show ->
            binding.otpLayout.visibility = if (show) View.VISIBLE else View.GONE
        }

        binding.loginBtn.setOnClickListener {
            val mobile = binding.mobileInput.text.toString()
            val otp = binding.otpInput.text.toString()

            if (mobile.length != 10 || !mobile.all { it.isDigit() }) {
                Toast.makeText(this,
                    getString(R.string.enter_a_valid_10_digit_mobile_number), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (otp.isEmpty()) {
                Toast.makeText(this, getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                DataStoreManager.setLoggedIn(true)
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }

        }
    }
}
package com.example.storyapp1.ui.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.storyapp1.R
import com.example.storyapp1.databinding.ActivityRegisterBinding
import com.example.storyapp1.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.btnRegister.isEnabled = s.toString().length >= 8
            }
        })
    }

    private fun setupViewModel() {
        registerViewModel.register.observe(this) { isSuccess ->
            if (isSuccess) {
                val dialogBuilder = AlertDialog.Builder(this)
                    .setTitle("Registration Successful")
                    .setPositiveButton("Oke") { _,_ ->
                        LoginActivity.start(this)
                        finish()
                    }
                    .setOnDismissListener {
                        LoginActivity.start(this)
                        finish()
                    }
                val dialog = dialogBuilder.create()
                dialog.show()
            }
        }

        registerViewModel.snackbarText.observe(this) { text ->
            when {
                text.contains("taken") -> {
                    binding.edtEmail.error = getString(R.string.email_created)
                    binding.edtEmail.requestFocus()
                }
                text.contains("created") ->{
                }
                text.contains("must be a valid email") -> {
                    binding.edtEmail.error = getString(R.string.email_must_valid)
                    binding.edtEmail.requestFocus()
                }
                text.contains("Password must be at least 8 characters long") -> {
                    binding.edtPassword.error = getString(R.string.error_password_min_8_char)
                    binding.edtPassword.requestFocus()
                }
                else -> Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(value: Boolean) {
        binding.btnLogin.isEnabled = !value
        binding.btnRegister.isInvisible = value
        binding.loading.isVisible = value
    }

    private fun setupAction() {
        with(binding) {
            btnLogin.setOnClickListener {
                LoginActivity.start(this@RegisterActivity)
            }

            btnRegister.setOnClickListener {
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                when {
                    name.isEmpty() -> {
                        edtName.error = "Name cannot be empty"
                        edtName.requestFocus()
                    }
                    email.isEmpty() -> {
                        edtEmail.error = "Email cannot be empty"
                        edtEmail.requestFocus()
                    }
                    password.isEmpty() -> {
                        edtPassword.error = "Passoword cannot be empty"
                        edtPassword.requestFocus()
                    }
                    else -> {
                        registerViewModel.register(name, email, password)
                    }
                }
            }
        }
    }
}
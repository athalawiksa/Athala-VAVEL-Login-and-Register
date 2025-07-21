package com.example.athalavovel

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnRegister = findViewById(R.id.btn_register)

        // Initialize SharedPreferences untuk Athala VOVEL
        sharedPreferences = getSharedPreferences("AthalaVovelUsers", Context.MODE_PRIVATE)

        // Set click listener for register button
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Validate input
        when {
            username.isEmpty() -> {
                etUsername.error = "Username tidak boleh kosong"
                etUsername.requestFocus()
                return
            }
            username.length < 3 -> {
                etUsername.error = "Username minimal 3 karakter"
                etUsername.requestFocus()
                return
            }
            password.isEmpty() -> {
                etPassword.error = "Password tidak boleh kosong"
                etPassword.requestFocus()
                return
            }
            password.length < 6 -> {
                etPassword.error = "Password minimal 6 karakter"
                etPassword.requestFocus()
                return
            }
            confirmPassword.isEmpty() -> {
                etConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
                etConfirmPassword.requestFocus()
                return
            }
            password != confirmPassword -> {
                etConfirmPassword.error = "Password tidak cocok"
                etConfirmPassword.requestFocus()
                return
            }
        }

        // Check if username already exists
        if (sharedPreferences.contains(username)) {
            Toast.makeText(this, "Username sudah terdaftar!", Toast.LENGTH_SHORT).show()
            return
        }

        // Save user data to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString(username, password)
        editor.apply()

        // Show success message and clear fields
        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun clearFields() {
        etUsername.text.clear()
        etPassword.text.clear()
        etConfirmPassword.text.clear()
        etUsername.requestFocus()
    }
}
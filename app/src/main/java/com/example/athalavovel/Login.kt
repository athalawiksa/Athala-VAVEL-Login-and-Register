package com.example.athalavovel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvRegister = findViewById(R.id.tv_register)

        // Initialize SharedPreferences (sama dengan RegisterActivity)
        sharedPreferences = getSharedPreferences("AthalaVovelUsers", Context.MODE_PRIVATE)

        // Set click listeners
        btnLogin.setOnClickListener {
            loginUser()
        }

        tvRegister.setOnClickListener {
            // Redirect ke RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validate input
        when {
            username.isEmpty() -> {
                etUsername.error = "Username tidak boleh kosong"
                etUsername.requestFocus()
                return
            }
            password.isEmpty() -> {
                etPassword.error = "Password tidak boleh kosong"
                etPassword.requestFocus()
                return
            }
        }

        // Cek apakah username terdaftar
        val savedPassword = sharedPreferences.getString(username, null)

        if (savedPassword == null) {
            Toast.makeText(this, "Username tidak terdaftar!", Toast.LENGTH_SHORT).show()
            etUsername.error = "Username tidak ditemukan"
            etUsername.requestFocus()
            return
        }

        // Cek password
        if (savedPassword == password) {
            // Login berhasil
            Toast.makeText(this, "Login berhasil! Selamat datang $username", Toast.LENGTH_SHORT).show()

            // Simpan status login
            val loginPrefs = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)
            val editor = loginPrefs.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.putString("currentUser", username)
            editor.apply()

            // Redirect ke MainActivity atau Dashboard
            // Ganti MainActivity dengan activity tujuan setelah login
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        } else {
            // Password salah
            Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show()
            etPassword.error = "Password tidak cocok"
            etPassword.requestFocus()
        }
    }

    override fun onResume() {
        super.onResume()
        // Clear fields ketika kembali ke login
        clearFields()
    }

    private fun clearFields() {
        etUsername.text.clear()
        etPassword.text.clear()
        etUsername.requestFocus()
    }
}
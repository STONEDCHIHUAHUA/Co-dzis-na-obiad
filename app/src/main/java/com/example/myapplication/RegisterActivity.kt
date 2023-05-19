package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        // znajdowanie guzikow/pol tekstu
        emailEditText = findViewById(R.id.register_emailEditText)
        passwordEditText = findViewById(R.id.register_passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        // przypisanie wartosci
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                // jesli nic nie jest wprowadzone
                Toast.makeText(this, "Podaj email i haslo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // zarajestruj uzytkownika
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // rejestracja poprawna przejdz do HomeActivity
                        Toast.makeText(this, "Rejestracja poprawna", Toast.LENGTH_SHORT).show()
                        val user = FirebaseAuth.getInstance().currentUser
                        val UserID = user?.email.toString()
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("UserID",UserID)
                        startActivity(intent)
                        finish()
                    } else {
                        // rejestracja niepoprawna
                        Toast.makeText(this, "Blad: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}
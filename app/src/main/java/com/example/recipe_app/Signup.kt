package com.example.recipe_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.recipe_app.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupbtn.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmpass = binding.signupConfirmpassword.text.toString()

            if (isInputValid(email, password, confirmpass)) {
                if (password == confirmpass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.backbtn.setOnClickListener{
            startActivity(Intent(this,intro_page::class.java))
        }
        binding.loginredirect.setOnClickListener {
            val loginIntent = Intent(this, Login::class.java)
            startActivity(loginIntent)
        }

        // Set up the onClickListener for password visibility toggle
        binding.viewPswd1.setOnClickListener {
            togglePasswordVisibility(binding.signupPassword)
        }

        // Set up the onClickListener for confirm password visibility toggle
        binding.viewPswd.setOnClickListener {
            togglePasswordVisibility(binding.signupConfirmpassword)
        }
    }

    private fun isInputValid(email: String, password: String, confirmPass: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPass) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isStrongPassword(password)) {
            Toast.makeText(this, "Password should be strong (e.g., include a combination of letters, numbers, and special characters)", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isStrongPassword(password: String): Boolean {
        // Add your custom password strength validation logic here
        // For example, check if the password contains a combination of letters, numbers, and special characters
        return password.matches(Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#\$%^&+=]).+\$"))
    }

    private fun togglePasswordVisibility(passwordEditText: EditText) {
        val isPasswordVisible =
            passwordEditText.transformationMethod is android.text.method.PasswordTransformationMethod

        // Toggle the password visibility
        passwordEditText.transformationMethod =
            if (isPasswordVisible) android.text.method.HideReturnsTransformationMethod.getInstance()
            else android.text.method.PasswordTransformationMethod.getInstance()

        // Move the cursor to the end of the text
        passwordEditText.setSelection(passwordEditText.text.length)
    }
}

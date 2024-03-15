package com.example.recipe_app

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipe_app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth




class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Set up onClickListeners
        binding.loginbtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageView>(R.id.login_google).setOnClickListener{
       intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
        binding.logintext.setOnClickListener {
            val sintent = Intent(this, Signup::class.java)
            startActivity(sintent)
        }

        binding.backbtn.setOnClickListener {
            startActivity(Intent(this, intro_page::class.java))
        }

        // Set up the onClickListener for the eye icon to toggle password visibility
        binding.viewPswd.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun togglePasswordVisibility() {
        val passwordEditText = binding.loginPassword
        val isPasswordVisible =
            passwordEditText.transformationMethod is PasswordTransformationMethod

        // Toggle the password visibility
        passwordEditText.transformationMethod =
            if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
            else PasswordTransformationMethod.getInstance()

        // Move the cursor to the end of the text
        passwordEditText.setSelection(passwordEditText.text.length)
    }


    fun resetPassword(view: View) {
        val email = binding.loginEmail.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show()
        }
        else{
          firebaseAuth.sendPasswordResetEmail(email)
              .addOnCompleteListener { task->
                  if(task.isSuccessful){
                      Toast.makeText(this, "Password Reset link sent please check email", Toast.LENGTH_SHORT).show()
                  }
else{
                      Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                  }
              }
        }


    }
}

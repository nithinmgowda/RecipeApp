package com.example.recipe_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.recipe_app.databinding.ActivityLoginBinding

import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        binding.loginbtn.setOnClickListener {
            val email =binding.loginEmail.text.toString()
            val password=binding.loginPassword.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "cant be empty", Toast.LENGTH_SHORT).show()
            }

        }
        binding.logintext.setOnClickListener {
         val sintent = Intent(this,Signup::class.java)
            startActivity(sintent)
        }
        binding.backbtn.setOnClickListener{
            startActivity(Intent(this,intro_page::class.java))
        }
    }
}
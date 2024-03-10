package com.example.recipe_app
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.example.recipe_app.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient= GoogleSignIn.getClient(this,gso)
        findViewById<ImageView>(R.id.google_btn).setOnClickListener{
            signInGoogle()
        }

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
    }
    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
private val launcher =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    result ->
    if(result.resultCode== Activity.RESULT_OK){

        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleResults(task)
    }
}

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account:GoogleSignInAccount? = task.result
            if (account!=null){
                updateUI(account)
            }

        }else{
            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
        }


    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
             val intent : Intent = Intent(this,MainActivity::class.java)
                intent.putExtra("email",account.email)
                intent.putExtra("name",account.displayName)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
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

}

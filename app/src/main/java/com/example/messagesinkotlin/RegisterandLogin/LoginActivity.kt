package com.example.messagesinkotlin.RegisterandLogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.messagesinkotlin.Messaging.LatestMessagesActivity
import com.example.messagesinkotlin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            loginTime()
        }

        accountCreateTextView.setOnClickListener{
            finish()
        }
    }

    private fun loginTime() {
        val email = accountLoginEmail.text.toString()
        val password = accountLoginPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)    //This clears all previous activity and makes it so clicking back closes the app.  Clear previous data
                startActivity(intent)
                //Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

// signInButton.setOnClickListener {               //User enters information and tries to log in
// val email = emailEditText.text.toString()
// val password = accountLoginPassword.text.toString()
//
// Log.d("Login", "Attempt login with email/password: $email/****")
//
// FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
// .addOnCompleteListener {
// if (!it.isSuccessful) return@addOnCompleteListener
//
// Log.d("Login", "Successfully logged in: ${it.result.user.uid}")
// }
// .addOnFailureListener {
// Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
// }
// }
//
// //User needs an account
// accountCreateTextView.setOnClickListener{       //User needs to create an account
// Log.d("LoginActivity", "Try to show main activity")
//
// //Launch login activity
// val intent = Intent(this, RegisterActivity::class.java)
// startActivity(intent)
// }*/
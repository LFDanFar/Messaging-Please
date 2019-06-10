package com.example.messagesinkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {               //User enters information and tries to log in
            val email = emailEditText.text.toString()
            val password = accountLoginPassword.text.toString()

            Log.d("Login", "Attempt login with email/password: $email/****")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                /*.addOnCompleteListener()
                .add*/
        }

        //User needs an account
        accountCreateTextView.setOnClickListener{       //User needs to create an account
            Log.d("LoginActivity", "Try to show main activity")

            //Launch login activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
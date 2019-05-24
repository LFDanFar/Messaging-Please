package com.example.messagesinkotlin

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = accountLoginPassword.text.toString()

            Log.d("Login", "Attempt login with email/password: $email/****")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                /*.addOnCompleteListener()
                .add*/
        }

        //User needs an account
        accountExistsTextView.setOnClickListener{
            Log.d("LoginActivity", "Try to show main activity")

            //Launch login activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
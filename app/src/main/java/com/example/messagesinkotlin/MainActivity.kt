package com.example.messagesinkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make button do stuff
        registerButton.setOnClickListener{
            //Easier accessing of textboxes on login screen
            val email = emailEditText.text.toString();
            val username = usernameEditText.text.toString();
            val password = passwordEditText.text.toString();

            Log.d("MainActivity", "Email is: " + email)
            Log.d("MainActivity", "Password: $password")   //$ does string format

            //Firebase goes here
        }

        //User has account?
        existingAccountTextView.setOnClickListener{
            Log.d("MainActivity", "Try to show login activity")

            //Launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

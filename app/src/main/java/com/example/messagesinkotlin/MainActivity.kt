package com.example.messagesinkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make button do stuff
        registerButton.setOnClickListener{
            registerUser()  //Reformatted my code
        }

        //User has account
        existingAccountTextView.setOnClickListener{
            Log.d("MainActivity", "Try to show login activity")

            //Launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    //Reformatted for cleanliness
    private fun registerUser() {
        //Easier accessing of textboxes on login screen
        val email = emailEditText.text.toString();
        val username = usernameEditText.text.toString();
        val password = passwordEditText.text.toString();

        if (email.isEmpty() || password.isEmpty()){ //Requires password & email
            Toast.makeText(this, "Please enter text in fields", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email is: " + email)
        Log.d("MainActivity", "Password: $password")   //$ does string format

        //Firebase goes here
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(!it.isSuccessful)    //Unsuccessful
                    return@addOnCompleteListener

                //Successful
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}")
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")   //Gives better message when user is not made
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

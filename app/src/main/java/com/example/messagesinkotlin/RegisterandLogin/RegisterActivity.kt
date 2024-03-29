package com.example.messagesinkotlin.RegisterandLogin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.messagesinkotlin.Messaging.LatestMessagesActivity
import com.example.messagesinkotlin.Models.User
import com.example.messagesinkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Make button do stuff
        registerUserButton.setOnClickListener{          //User created account
            registerUser()  //Reformatted my code
        }
        existingAccountTextView.setOnClickListener{     //User has account
            //Launch LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        selectPhotoButton.setOnClickListener{           //Make the round button choose a picture
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {   //Make it so photo may be chosen
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){      //Photo selection
            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectPhotoImageView.setImageBitmap(bitmap)     //Allows for image to be chosen
            selectPhotoButton.alpha = 0f                    //Changes photo button so round picture may be viewed
        }
    }



    //Reformatted for cleanliness
    private fun registerUser() {    //Register button, sends info to FireBase
        //Easier accessing of textboxes on login screen
        val email = emailEditText.text.toString();
        val password = passwordEditText.text.toString();

        if (email.isEmpty() || password.isEmpty()){ //Requires password & email
            //Toast is the little grey window that pops up at the bottom when something happens
            Toast.makeText(this, "Please enter text in fields", Toast.LENGTH_SHORT).show()
            return
        }

            //I'll leave this in
        Log.d("RegisterActivity", "Email is: " + email)
        Log.d("RegisterActivity", "Password: $password")   //$ does string format

        //Firebase goes here
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)  //Creates account
            .addOnCompleteListener{
                if(!it.isSuccessful)    //Unsuccessful
                    return@addOnCompleteListener

                //Successfully made user
                Log.d("RegisterActivity", "Successfully created user with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show() //Account created

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")   //Gives better message when user is not made
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null)
            return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")   //Tracks storage of image

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {     //Photo successfully implemented
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it") //Shows location of image

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                //
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, usernameEditText.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Saved user to Firebase Database")    //User saved to database

                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)    //This clears all previous activity and makes it so clicking back closes the app.  Clear previous data
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to set value to database: ${it.message}") //User not created
            }
    }
}
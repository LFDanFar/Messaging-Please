package com.example.messagesinkotlin.Messaging

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.messagesinkotlin.Models.ChatMessage
import com.example.messagesinkotlin.Models.User
import com.example.messagesinkotlin.R
import com.example.messagesinkotlin.Views.ChatFromItem
import com.example.messagesinkotlin.Views.ChatToItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chatlog.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatlogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()

    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlog)

        recyclerviewChatlog.adapter = adapter

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = toUser?.username

        listenMessages()

        sendMessageButton.setOnClickListener{   //Sends messages
            actuallySendMessage()
        }
    }

    private fun actuallySendMessage(){
        val text = editTextChatlog.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user.uid

        if (fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()      //Messages stored with IDs so users can get their correct messages
        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis()/1000)     //Remove these if I don't want to store specific information

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()    //Receive messages from other users


        reference.setValue(chatMessage)
            .addOnSuccessListener { //Saves messages in chatlog
                editTextChatlog.text.clear()    //Clears textbox for messages
                recyclerviewChatlog.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)

        val latestMessageReference = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")      //Creates node that stores information for user sending
        latestMessageReference.setValue(chatMessage)
        val latestMessageToReference = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")    //Stores information for user receiving
        latestMessageToReference.setValue(chatMessage)
    }

    private fun listenMessages(){
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null){   //Updates messages
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatFromItem(chatMessage.text, currentUser))
                    }
                    else{
                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }
                }

                recyclerviewChatlog.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                //
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                //
            }
        })
    }
}


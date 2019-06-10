package com.example.messagesinkotlin.Messaging

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.messagesinkotlin.Models.ChatMessage
import com.example.messagesinkotlin.Models.User
import com.example.messagesinkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chatlog.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatlogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlog)

        recyclerviewChatlog.adapter = adapter

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = user.username

        listenMessages()

        sendMessageButton.setOnClickListener{
            Log.d("Chatlog", "Send")
            actuallySendMessage()
        }
    }

    private fun actuallySendMessage(){
        val text = editTextChatlog.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user.uid

        if (fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis()/1000)     //Remove these if I don't want to store specific information

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("Chatlog", "Saved chat message: ${reference.key}")
            }
    }

    private fun listenMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null){
                    Log.d("Chatlog", chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
                        adapter.add(ChatFromItem(chatMessage.text))
                    }
                    else{
                        adapter.add(ChatToItem(chatMessage.text))
                    }
                }
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
        /*val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatToItem("To\nTo"))
        adapter.add(ChatFromItem("From"))
        adapter.add(ChatToItem("To\nTo"))
        adapter.add(ChatFromItem("From"))
        adapter.add(ChatToItem("To\nTo"))
        adapter.add(ChatFromItem("From"))

        recyclerviewChatlog.adapter = adapter*/
    }
}

class ChatFromItem(val text: String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewFromRow.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}
class ChatToItem(val text: String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewToRow.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
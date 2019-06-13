package com.example.messagesinkotlin.Views

import com.example.messagesinkotlin.Models.ChatMessage
import com.example.messagesinkotlin.Models.User
import com.example.messagesinkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){
    var chatMateUser: User? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewLatestMessageMessage.text = chatMessage.text

        val chatMateId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatMateId = chatMessage.toId
        }
        else
            chatMateId = chatMessage.fromId

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatMateId")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatMateUser = p0.getValue(User::class.java)
                viewHolder.itemView.textViewLatestMessageUsername.text = chatMateUser?.username

                val targetImageView = viewHolder.itemView.imageViewLatestMessage
                Picasso.get().load(chatMateUser?.profileImageUrl).into(targetImageView)
            }
            override fun onCancelled(p0: DatabaseError) {
                //
            }
        })
    }
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}
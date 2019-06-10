package com.example.messagesinkotlin.Messaging

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.messagesinkotlin.Models.User
import com.example.messagesinkotlin.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chatlog.*
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatlogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatlog)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = user.username

        dummyData()
    }

    private fun dummyData(){
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())

        recyclerviewChatlog.adapter = adapter
    }
}

class ChatFromItem: Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        //
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}
class ChatToItem: Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        //
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
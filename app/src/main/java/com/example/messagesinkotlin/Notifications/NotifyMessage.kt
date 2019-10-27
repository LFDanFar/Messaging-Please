package com.example.messagesinkotlin.Notifications

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.provider.Settings.Global.getString
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.app.NotificationCompat
import com.example.messagesinkotlin.Messaging.LatestMessagesActivity
import com.example.messagesinkotlin.R
/*
class NotifyMessage{
    companion object{
        private const val CHANNEL_ID = "Message_Notification"
        private const val CHANNEL_NAME = "Confer"

        @RequiresApi(Build.VERSION_CODES.O)
        fun getBuilding(context: Context, channelId: String){
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Confer Notifies")
                .setContentText("Time to Confer!")
                .setContentIntent(getPendingIntent(context, LatestMessagesActivity::class.java))
            //.setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(CHANNEL_ID)

            notificationManager.notify(CHANNEL_NAME, builder.build())
        }

        private fun <T> getPendingIntent(context: Context, javaClass: Class<T>): PendingIntent{
            val intent = Intent(context, javaClass)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(intent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun NotificationManager.createNotificationChannel(channelId: String, chanName: CharSequence) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            /*val chanName = "Confer Notification"
            val descriptionText = "Notified User"*/

            val importance = NotificationManager.IMPORTANCE_DEFAULT //Min, low and high also exist
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)

            mChannel.enableLights(true)
            mChannel.lightColor = Color.GREEN
            mChannel.lockscreenVisibility
            mChannel.enableVibration(true)
            mChannel.audioAttributes
            mChannel.canShowBadge()
            this.createNotificationChannel(mChannel)
            //mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            /*val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)*/
        }
    }
}*/
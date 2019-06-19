package com.example.messagesinkotlin.Notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
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
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.app.NotificationCompat
import com.example.messagesinkotlin.R

@SuppressLint("ParcelCreator")
class NotifyMessage(id: String, name: CharSequence, importance: Int) : Parcelable{
    companion object CREATOR : Parcelable.Creator<NotifyMessage> {
        override fun createFromParcel(parcel: Parcel): NotifyMessage {
            return NotifyMessage(parcel)
        }

        private fun NotifyMessage(parcel: Parcel): NotifyMessage {
            //
        }

        override fun newArray(size: Int): Array<NotifyMessage?> {
            return arrayOfNulls(size)
        }
        private const val CHANNEL_ID = "Message_Notification"
        private const val CHANNEL_NAME = "Confer"
    }

        //Notification Fanciness
    /*fun alerts(){
        //Bubbles
        fun setAllowBubbles(allowBubbles: Boolean): Unit{
            //
        }
        fun canBubble(): Boolean{
            setAllowBubbles()
        }
        //Lights
        fun enableLights(lights: Boolean): Unit{
            //
        }
        fun shouldShowLights(): Boolean{
            enableLights()
            return enableLights("Green", )
        }
        fun getLightColor(): Int{
            //
        }
        fun setLightColor(argb: Int): Unit{

        }
        //Vibrations
        fun enableVibration(vibration: Boolean): Unit{
            //
        }
        fun shouldVibrate(): Boolean{
            enableVibration()
        }
        fun getVibrationPattern(): LongArray{
            shouldVibrate()
        }
        //Other stuff
        fun getName(): CharSequence{
            getName()
        }
        fun getLockscreenVisibility(): Int{
            getLockscreenVisibility()
        }
        fun getSound(): Uri {
            //
        }
        fun getGroup(): String{
            //
        }
        fun getDescription(): String{
            //
        }
        fun getAudioAttributes(): AudioAttributes {
            //
        }
        fun canShowBadge(): Boolean{
            //
        }
    }*/

    constructor(parcel: Parcel, id: String, name: CharSequence, importance: Int) : this {
        //
    }

    fun getBuilding(context: Context){
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Confer Notifies")
            .setContentText("Time to Confer!")
            //.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)

        notificationManager.notify(, builder.build)
    }

    private fun NotificationManager.notificationMessages(channelId: String, chanName: CharSequence) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            /*val chanName = "Confer Notification"
            val descriptionText = "Notified User"*/

            val importance = NotificationManager.IMPORTANCE_DEFAULT //Min, low and high also exist
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)

            mChannel.enableLights(true)
            mChannel.lightColor = Color.GREEN
            this.createNotificationChannel(mChannel)
            //mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            /*val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)*/
        }
    }

    private fun getSystemService(notificatioN_SERVICE: String): Any {
        //
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //
    }
    override fun describeContents(): Int {
        return 0
    }
}
package com.example.messagesinkotlin.Models

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String): Parcelable{
    constructor() : this("", "", "")
}

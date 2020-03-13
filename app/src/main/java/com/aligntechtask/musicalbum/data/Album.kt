package com.aligntechtask.musicalbum.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album (
/*List item data*/
    val id: String,
    val title: String,
    val image: String,
    val description: String

):  Parcelable{
    val id1: String? = null
    val title1: String = ""
    val image1: String = ""
    val description1: String = ""


}
package com.example.dpei_2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(val name: String, val degree: Double):Parcelable

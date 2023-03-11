package com.example.wardrobeapp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "pants")
data class Pant(
    @PrimaryKey(autoGenerate = true)
    val pant_id : Int,
    val pant_img : String
)

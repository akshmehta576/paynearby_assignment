package com.example.wardrobeapp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "shirts")
data class Shirt(
    @PrimaryKey(autoGenerate = true)
    val shirt_id: Int,
    val shirt_img: String
)

package com.example.wardrobeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wardrobeapp.model.Pant
import com.example.wardrobeapp.model.Shirt

@Dao
interface WardRobeDao {
    @Insert
    suspend fun upsertpant(pant: Pant)

    @Query("SELECT * FROM pants")
    fun getAllPants(): LiveData<List<Pant>>

    @Insert
    suspend fun upsertshirt(shirt: Shirt)

    @Query("SELECT * FROM shirts")
    fun getAllShirts(): LiveData<List<Shirt>>
}
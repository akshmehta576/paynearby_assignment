package com.example.wardrobeapp

import androidx.lifecycle.LiveData
import com.example.wardrobeapp.dao.WardRobeDao
import com.example.wardrobeapp.model.Pant
import com.example.wardrobeapp.model.Shirt

class Repository(private val wardRobeDao: WardRobeDao) {

    val allShirts: LiveData<List<Shirt>> = wardRobeDao.getAllShirts()

    suspend fun insertShirt(shirt: Shirt) {
        wardRobeDao.upsertshirt(shirt)
    }

    val allPants: LiveData<List<Pant>> = wardRobeDao.getAllPants()

    suspend fun insertPant(pant: Pant) {
        wardRobeDao.upsertpant(pant)
    }


}
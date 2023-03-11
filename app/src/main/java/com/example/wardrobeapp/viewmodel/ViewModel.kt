package com.example.wardrobeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wardrobeapp.Repository
import com.example.wardrobeapp.db.WardRobeDatabase
import com.example.wardrobeapp.model.Pant
import com.example.wardrobeapp.model.Shirt
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val allShirts: LiveData<List<Shirt>>
    val allPants: LiveData<List<Pant>>

    init {
        val dao = WardRobeDatabase.getDatabase(application).getDao()
        repository = Repository(dao)
        allShirts = repository.allShirts
        allPants = repository.allPants
    }

    fun insertShirt(shirt: Shirt) =

        viewModelScope.launch { repository.insertShirt(shirt) }

    fun insertPant(pant: Pant) =
        viewModelScope.launch { repository.insertPant(pant) }


}
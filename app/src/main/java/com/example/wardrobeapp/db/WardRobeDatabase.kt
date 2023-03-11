package com.example.wardrobeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wardrobeapp.Converters
import com.example.wardrobeapp.dao.WardRobeDao
import com.example.wardrobeapp.model.Pant
import com.example.wardrobeapp.model.Shirt

@Database(
    entities = [Shirt::class, Pant::class],
    version = 1, exportSchema = false
)

@TypeConverters(Converters::class)
abstract class WardRobeDatabase : RoomDatabase() {
    abstract fun getDao(): WardRobeDao

    companion object {
        @Volatile // update every thread
        private var INSTANCE: WardRobeDatabase? = null

        fun getDatabase(context: Context): WardRobeDatabase {

            if (INSTANCE == null) {
                synchronized(this) // for multiple threading
                {
                    INSTANCE =
                        Room.databaseBuilder(context, WardRobeDatabase::class.java, "wardrobe_Db")
                            .fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }

}
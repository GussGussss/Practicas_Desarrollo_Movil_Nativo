package com.example.practica_8_sqlite_room_database.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practica_8_sqlite_room_database.data.Usuario
import com.example.practica_8_sqlite_room_database.data.UsuarioDao

@Database(entities = [Usuario::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun UsuarioDao(): UsuarioDao

    companion object{
        @Volatile
        private var INSTACE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            return INSTACE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mi_base_datos"
                ).build()
                INSTACE = instance
                instance
            }
        }
    }
}
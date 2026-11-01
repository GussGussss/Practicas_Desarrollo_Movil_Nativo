package com.example.mini_proyecto_red_social_local.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PublicacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(publicacion: Publicacion)

    @Query("SELECT * FROM publicaciones ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Publicacion>>
}
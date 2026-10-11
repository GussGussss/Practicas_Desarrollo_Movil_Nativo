package com.example.practica_8_sqlite_room_database.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): Flow<List<Usuario>>

    @Insert
    fun insertar(usuario:Usuario)
    @Delete
    fun eliminar(usuario: Usuario)
}
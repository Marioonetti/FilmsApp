package com.example.appentretenimiento.data.sources.local

import androidx.room.*
import com.example.appentretenimiento.data.model.database.PeliculaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PeliculasDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPeliculaFav(peliculaEntity: PeliculaEntity)

    @Query("select * from peliculas")
    fun getAllFavPeliculas(): Flow<List<PeliculaEntity>>

    @Query("select count(id) from peliculas where id = :id")
    suspend fun checkId(id: Int): Int

    @Query("delete from peliculas where id = :id")
    suspend fun delByid(id: Int)

    @Update
    suspend fun addPuntuacionPersonal(peliculaEntity: PeliculaEntity)

    @Query("select * from peliculas where id = :id")
    fun getLocalFavFilm(id: Int): Flow<PeliculaEntity>


}
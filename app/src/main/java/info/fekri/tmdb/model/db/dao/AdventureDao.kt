package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.Adventure

@Dao
interface AdventureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAdventure(adventures:List<Adventure>)

    @Query("SELECT * FROM adventure_movie_table")
    suspend fun getAllAdventure(): List<Adventure>

    @Query("SELECT * FROM adventure_movie_table WHERE id = :movieId")
    suspend fun getByIdAdventure(movieId: Int): Adventure

}
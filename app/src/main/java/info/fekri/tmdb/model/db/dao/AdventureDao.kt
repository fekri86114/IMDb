package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.*

@Dao
interface AdventureDao {

    @Query("SELECT * FROM advent_table")
    suspend fun getAllAdvents(): List<Adventure>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(adventure: List<Adventure>)

    @Query("SELECT * FROM advent_table WHERE id = :movieId")
    suspend fun getAdventureById(movieId: Int): Adventure

}
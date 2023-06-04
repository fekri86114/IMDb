package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.Fantasy

@Dao
interface FantasyDao {

    @Query("SELECT * FROM fantasy_table")
    suspend fun getAllFantasies(): List<Fantasy>

    @Query("SELECT * FROM fantasy_table WHERE id = :movieId")
    suspend fun getFantasyById(movieId: Int): Fantasy

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(fantasy: List<Fantasy>)

}
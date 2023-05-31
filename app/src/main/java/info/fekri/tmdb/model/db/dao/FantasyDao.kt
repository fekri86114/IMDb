package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.Fantasy

@Dao
interface FantasyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateFantasy(fantasies:List<Fantasy>)

    @Query("SELECT * FROM fantasy_movie_table")
    suspend fun getAllFantasy(): List<Fantasy>

    @Query("SELECT * FROM fantasy_movie_table WHERE id = :movieId")
    suspend fun getByIdFantasy(movieId: Int): Fantasy

}
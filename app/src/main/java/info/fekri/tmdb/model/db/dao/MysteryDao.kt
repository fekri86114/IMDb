package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.Mystery

@Dao
interface MysteryDao {

    @Query("SELECT * FROM mystery_table")
    suspend fun getAllMysteries(): List<Mystery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(mystery: List<Mystery>)

    @Query("SELECT * FROM mystery_table WHERE id = :movieId")
    suspend fun getMysteryById(movieId: Int): Mystery

}
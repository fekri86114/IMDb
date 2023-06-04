package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.Scientific

@Dao
interface ScientificDao {

    @Query("SELECT * FROM scientific_table")
    suspend fun getAllScientific(): List<Scientific>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(scientific: List<Scientific>)

    @Query("SELECT * FROM scientific_table WHERE id = :movieId")
    suspend fun getScientificById(movieId: Int): Scientific

}
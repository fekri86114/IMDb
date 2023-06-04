package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.Horror

@Dao
interface HorrorDao {

    @Query("SELECT * FROM horror_table")
    suspend fun getAllHorrors(): List<Horror>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(horror: List<Horror>)

    @Query("SELECT * FROM horror_table WHERE id = :movieId")
    suspend fun getHorrorById(movieId: Int): Horror

}
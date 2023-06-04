package info.fekri.tmdb.model.db.dao

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.Drama

@Dao
interface DramaDao {

    @Query("SELECT * FROM drama_table")
    suspend fun getAllDramas(): List<Drama>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(drama: List<Drama>)

    @Query("SELECT * FROM drama_table WHERE id = :movieId")
    suspend fun getDramaById(movieId: Int): Drama

}
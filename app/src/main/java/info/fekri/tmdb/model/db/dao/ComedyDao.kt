package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.Comedy

@Dao
interface ComedyDao {

    @Query("SELECT * FROM comedy_table")
    suspend fun getAllComedies(): List<Comedy>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(comedy: List<Comedy>)

    @Query("SELECT * FROM comedy_table WHERE id = :comedyId")
    suspend fun getComedyById(comedyId: Int): Comedy

}
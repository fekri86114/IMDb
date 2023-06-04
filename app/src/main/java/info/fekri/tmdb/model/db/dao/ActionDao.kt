package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.movie.*

@Dao
interface ActionDao {

    @Query("SELECT * FROM action_table")
    suspend fun getAllActions(): List<Action>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(action: List<Action>)

    @Query("SELECT * FROM action_table WHERE id = :movieId")
    suspend fun getActionById(movieId: Int): Action

}
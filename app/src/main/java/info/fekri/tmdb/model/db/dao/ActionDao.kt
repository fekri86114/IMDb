package info.fekri.tmdb.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.fekri.tmdb.model.data.Action

@Dao
interface ActionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAction(actions:List<Action>)

    @Query("SELECT * FROM action_movie_table")
    suspend fun getAllAction(): List<Action>

    @Query("SELECT * FROM action_movie_table WHERE id = :movieId")
    suspend fun getByIdAction(movieId: Int): Action

}
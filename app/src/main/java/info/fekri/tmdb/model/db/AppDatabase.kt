package info.fekri.tmdb.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.fekri.tmdb.model.data.*
import info.fekri.tmdb.model.db.dao.*

@Database(
    entities = [
        Action::class,
        Fantasy::class,
        Adventure::class,
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
    abstract fun fantasyDao(): FantasyDao
    abstract fun adventureDao(): AdventureDao
}
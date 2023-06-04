package info.fekri.tmdb.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.fekri.tmdb.model.data.movie.Action
import info.fekri.tmdb.model.data.movie.Adventure
import info.fekri.tmdb.model.data.movie.Comedy
import info.fekri.tmdb.model.data.movie.Drama
import info.fekri.tmdb.model.data.movie.Fantasy
import info.fekri.tmdb.model.data.movie.Horror
import info.fekri.tmdb.model.data.movie.Mystery
import info.fekri.tmdb.model.data.movie.Scientific
import info.fekri.tmdb.model.db.dao.ActionDao
import info.fekri.tmdb.model.db.dao.AdventureDao
import info.fekri.tmdb.model.db.dao.ComedyDao
import info.fekri.tmdb.model.db.dao.DramaDao
import info.fekri.tmdb.model.db.dao.FantasyDao
import info.fekri.tmdb.model.db.dao.HorrorDao
import info.fekri.tmdb.model.db.dao.MysteryDao
import info.fekri.tmdb.model.db.dao.ScientificDao

@Database(
    entities = [Action::class, Adventure::class, Comedy::class, Drama::class, Fantasy::class, Horror::class, Mystery::class, Scientific::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun actionDao(): ActionDao
    abstract fun adventureDao(): AdventureDao
    abstract fun comedyDao(): ComedyDao
    abstract fun dramaDao(): DramaDao
    abstract fun fantasyDao(): FantasyDao
    abstract fun horrorDao(): HorrorDao
    abstract fun mysteryDao(): MysteryDao
    abstract fun scientificDao(): ScientificDao

}
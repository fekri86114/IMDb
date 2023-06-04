package info.fekri.tmdb.di

import androidx.room.Room
import info.fekri.tmdb.model.db.AppDatabase
import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.MovieRepository
import info.fekri.tmdb.model.repository.MovieRepositoryImpl
import info.fekri.tmdb.ui.feature.main.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "imdb_movies_database.db",
        ).build()
    }

    single<MovieRepository> {
        MovieRepositoryImpl(
            apiService = get(),
            get<AppDatabase>().actionDao(),
            get<AppDatabase>().adventureDao(),
            get<AppDatabase>().comedyDao(),
            get<AppDatabase>().dramaDao(),
            get<AppDatabase>().fantasyDao(),
            get<AppDatabase>().horrorDao(),
            get<AppDatabase>().mysteryDao(),
            get<AppDatabase>().scientificDao()
        )
    }

    viewModel { (isNetConnected: Boolean) -> MainScreenViewModel(get(), isNetConnected) }
}
package info.fekri.tmdb.di

import androidx.room.Room
import info.fekri.tmdb.model.db.AppDatabase
import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.ProductRepository
import info.fekri.tmdb.model.repository.ProductRepositoryImpl
import info.fekri.tmdb.ui.feature.main.MainScreenViewModel
import info.fekri.tmdb.ui.feature.start.StartScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "imdb_database.db").build()
    }

    single<ProductRepository>{ ProductRepositoryImpl(get(), get<AppDatabase>().productDao()) }

    viewModel { StartScreenViewModel() }
    viewModel { (isNetConnected: Boolean) -> MainScreenViewModel(get(), isNetConnected) }
}
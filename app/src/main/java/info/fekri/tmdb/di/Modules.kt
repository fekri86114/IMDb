package info.fekri.tmdb.di

import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.MovieRepository
import info.fekri.tmdb.model.repository.MovieRepositoryImpl
import info.fekri.tmdb.ui.feature.main.MainScreenViewModel
import info.fekri.tmdb.ui.feature.start.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }

    single<MovieRepository> { MovieRepositoryImpl(get()) }

    viewModel { StartScreenViewModel() }
    viewModel { (isNetConnected: Boolean) -> MainScreenViewModel(get(), isNetConnected) }
}
package info.fekri.tmdb.di

import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.MovieRepository
import info.fekri.tmdb.model.repository.MovieRepositoryImpl
import info.fekri.tmdb.ui.feature.category.CategoryViewModel
import info.fekri.tmdb.ui.feature.detail.DetailViewModel
import info.fekri.tmdb.ui.feature.main.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }

    single<MovieRepository> { MovieRepositoryImpl(apiService = get()) }

    viewModel { (isNetConnected: Boolean) -> MainScreenViewModel(get(), isNetConnected) }
    viewModel { DetailViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
}
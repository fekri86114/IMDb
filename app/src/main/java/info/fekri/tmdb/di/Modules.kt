package info.fekri.tmdb.di

import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.movie.MovieRepository
import info.fekri.tmdb.model.repository.movie.MovieRepositoryImpl
import info.fekri.tmdb.model.repository.search.SearchRepository
import info.fekri.tmdb.model.repository.search.SearchRepositoryImpl
import info.fekri.tmdb.ui.feature.category.CategoryViewModel
import info.fekri.tmdb.ui.feature.detail.DetailViewModel
import info.fekri.tmdb.ui.feature.main.MainScreenViewModel
import info.fekri.tmdb.ui.feature.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }

    single<MovieRepository> { MovieRepositoryImpl(apiService = get()) }
    single<SearchRepository> { SearchRepositoryImpl(apiService = get()) }

    viewModel { (isNetConnected: Boolean) -> MainScreenViewModel(get(), isNetConnected) }
    viewModel { DetailViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}
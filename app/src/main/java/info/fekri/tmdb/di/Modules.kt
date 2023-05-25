package info.fekri.tmdb.di

import android.content.Context
import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.detail.DetailRepository
import info.fekri.tmdb.model.repository.detail.DetailRepositoryImpl
import info.fekri.tmdb.model.repository.main.MainRepository
import info.fekri.tmdb.model.repository.main.MainRepositoryImpl
import info.fekri.tmdb.ui.feature.detail.DetailViewModel
import info.fekri.tmdb.ui.feature.main.MainViewModel
import info.fekri.tmdb.ui.feature.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }
    single { createApiService() }

    single<MainRepository> { MainRepositoryImpl(get()) }
    single<DetailRepository> { DetailRepositoryImpl(get()) }

    viewModel { (isNetConnected: Boolean) -> MainViewModel(get(), isNetConnected) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
package info.fekri.tmdb.di

import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.main.MainRepository
import info.fekri.tmdb.model.repository.main.MainRepositoryImpl
import info.fekri.tmdb.ui.feature.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }

    single<MainRepository> { MainRepositoryImpl(get()) }

    viewModel { (isNetConnected: Boolean) -> MainViewModel(get(), isNetConnected) }
}
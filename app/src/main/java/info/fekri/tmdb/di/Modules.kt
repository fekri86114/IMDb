package info.fekri.tmdb.di

import info.fekri.tmdb.model.net.createApiService
import info.fekri.tmdb.model.repository.MainScreenRepository
import info.fekri.tmdb.model.repository.MainScreenRepositoryImpl
import info.fekri.tmdb.ui.feature.main.MainScreenViewModel
import info.fekri.tmdb.ui.feature.start.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }

//    single<MainScreenRepository> { (isNetConnected: Boolean) ->
//        MainScreenRepositoryImpl(get(), isNetConnected)
//    }

//    viewModel { MainScreenViewModel(get()) }
    viewModel { StartScreenViewModel() }
}
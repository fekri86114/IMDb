package info.fekri.tmdb.di

import info.fekri.tmdb.ui.feature.start.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {


    viewModel { StartScreenViewModel() }
}
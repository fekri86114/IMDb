package info.fekri.tmdb.ui.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.Action
import info.fekri.tmdb.model.data.Adventure
import info.fekri.tmdb.model.data.Fantasy
import info.fekri.tmdb.model.repository.MovieRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val movieRepository: MovieRepository,
    isNetConnected: Boolean
) : ViewModel() {
    val dataActions = mutableStateOf<List<Action>>(listOf())
    val dataAdventures = mutableStateOf<List<Adventure>>(listOf())
    val dataFantasies = mutableStateOf<List<Fantasy>>(listOf())
    val showProgress = mutableStateOf(false)

    init {
        refreshAllDataFromNet(isNetConnected)
    }

    private fun refreshAllDataFromNet(isNetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isNetConnected) {
                showProgress.value = true

                dataActions.value = movieRepository.getAllActions(isNetConnected)

                showProgress.value = false
            }
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isNetConnected) {
                showProgress.value = true

                dataFantasies.value = movieRepository.getAllFantasies(isNetConnected)

                showProgress.value = false
            }
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isNetConnected) {
                showProgress.value = true

                dataAdventures.value = movieRepository.getAllAdventure(isNetConnected)

                showProgress.value = false
            }
        }

    }

}
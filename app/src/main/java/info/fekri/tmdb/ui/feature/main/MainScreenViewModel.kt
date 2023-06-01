package info.fekri.tmdb.ui.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.Action
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
    val dataFantasies = mutableStateOf<List<Fantasy>>(listOf())
    val showProgress = mutableStateOf(false)

    init {
        refreshAllDataFromNet(isNetConnected)
    }

    private fun refreshAllDataFromNet(isNetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isNetConnected) {
                showProgress.value = true

                val dataActionToSet = async { movieRepository.getAllActions(isNetConnected) }
                val dataFantasiesToSet = async { movieRepository.getAllFantasies(isNetConnected) }

                updateDataMovies(
                    dataActionToSet.await(),
                    dataFantasiesToSet.await()
                )

                showProgress.value = false
            }
        }

    }

    private fun updateDataMovies(
        actionsData: List<Action>,
        fantasies: List<Fantasy>
    ) {
        dataActions.value = actionsData
        dataFantasies.value = fantasies
    }

}
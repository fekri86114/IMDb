package info.fekri.tmdb.ui.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.MovieByQuery
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.net.ApiService
import info.fekri.tmdb.model.repository.main.MainRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository,
    isInternetConnected: Boolean
) : ViewModel() {
    val actionMovies = mutableStateOf(listOf<QueryResult>())
    val animationMovies = mutableStateOf(listOf<QueryResult>())
    val mysteryMovies = mutableStateOf(listOf<QueryResult>())
    val showProgress = mutableStateOf(false)

    init {
        refreshDataFromNet(isInternetConnected)
    }

    private fun refreshDataFromNet(isInternetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {

            if (isInternetConnected) {
                showProgress.value = true
                delay(1200)

                // get data from net
                async {
                    updateData(
                        mainRepository.getActionMovies(isInternetConnected),
                        mainRepository.getAnimationMovies(isInternetConnected),
                        mainRepository.getMysteryMovies(isInternetConnected)
                    )
                }

                showProgress.value = false
            }

        }

    }

    private fun updateData(
        actions: List<QueryResult>,
        animations: List<QueryResult>,
        mysteries: List<QueryResult>
    ) {

        actionMovies.value = actions
        animationMovies.value = animations
        mysteryMovies.value = mysteries

    }


}
package info.fekri.tmdb.ui.feature.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.MovieById
import info.fekri.tmdb.model.repository.main.MainRepository
import info.fekri.tmdb.util.EMPTY_MOVIE_BY_ID
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel(
    private val mainRepository: MainRepository,
    private val movieId: Int
): ViewModel() {
    val detailMovie = mutableStateOf(EMPTY_MOVIE_BY_ID)
    val showProgress = mutableStateOf(false)

    init {
        loadMovieById(movieId)
    }

    private fun loadMovieById(movieId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            showProgress.value = true
            delay(2000)

            async {
                updateData(mainRepository.getMovieById(movieId))
            }

            showProgress.value = false
        }
    }

    private fun updateData(movieById: MovieById) {
        detailMovie.value = movieById
    }

}
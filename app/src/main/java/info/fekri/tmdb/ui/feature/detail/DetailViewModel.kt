package info.fekri.tmdb.ui.feature.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.MovieId
import info.fekri.tmdb.model.repository.movie.MovieRepository
import info.fekri.tmdb.util.EMPTY_MOVIE_ID
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    val movieById = mutableStateOf<MovieId>(EMPTY_MOVIE_ID)
    val showProgress = mutableStateOf(false)

    fun loadData(id: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            showProgress.value = true

            val dataMovieIdToSet = movieRepository.getMovieById(id)
            updateData(dataMovieIdToSet)

            showProgress.value = false
        }
    }

    private fun updateData(movieId: MovieId) {
        movieById.value = movieId
    }

}
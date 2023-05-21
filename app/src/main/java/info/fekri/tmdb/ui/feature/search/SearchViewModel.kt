package info.fekri.tmdb.ui.feature.search

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

class SearchViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    val showProgress = mutableStateOf(false)
    val movieById = mutableStateOf(EMPTY_MOVIE_BY_ID)

    fun getItemById(id: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            showProgress.value = true
            delay(2000)

            movieById.value = mainRepository.getMovieById(id)

            showProgress.value = false
        }
    }

}
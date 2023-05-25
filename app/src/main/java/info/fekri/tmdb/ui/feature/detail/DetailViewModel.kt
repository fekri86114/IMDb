package info.fekri.tmdb.ui.feature.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.net.ApiService
import info.fekri.tmdb.model.repository.detail.DetailRepository
import info.fekri.tmdb.util.EMPTY_MOVIE_BY_ID
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailRepository: DetailRepository
) : ViewModel() {
    val thisMovie = mutableStateOf(EMPTY_MOVIE_BY_ID)
    val showProgress = mutableStateOf(false)

    fun loadData(movieId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            showProgress.value = true

            thisMovie.value = detailRepository.getMovieById(movieId)

            showProgress.value = false
        }
    }

}
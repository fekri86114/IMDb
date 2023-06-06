package info.fekri.tmdb.ui.feature.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.repository.movie.MovieRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {
    val categoryData = mutableStateOf<List<QueryResult>>(listOf())

    fun loadDataByCategory(category: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val dataCategoryToSet = movieRepository.getMovieByQuery(category)
            updateData(dataCategoryToSet)
        }
    }

    private fun updateData(data: List<QueryResult>) {
        categoryData.value = data
    }

}
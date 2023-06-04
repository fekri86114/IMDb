package info.fekri.tmdb.ui.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.repository.MovieRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {


    fun loadDataByCategory(category: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

        }

    }

}
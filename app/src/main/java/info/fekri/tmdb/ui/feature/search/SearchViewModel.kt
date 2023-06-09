package info.fekri.tmdb.ui.feature.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.repository.search.SearchRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    val search = MutableLiveData("")
    val dataSearch = mutableStateOf<List<QueryResult>>(listOf())
    val showProgress = mutableStateOf(false)
    val showContent = mutableStateOf(false)

    fun loadDataSearch(search: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            showProgress.value = true

            val dataSearchToSet = searchRepository.getMusicBySearch(search)
            updateDataSearch(dataSearchToSet)
            showContent.value = true
            showProgress.value = false
        }
    }

    private fun updateDataSearch(data: List<QueryResult>) {
        dataSearch.value = data
    }

}
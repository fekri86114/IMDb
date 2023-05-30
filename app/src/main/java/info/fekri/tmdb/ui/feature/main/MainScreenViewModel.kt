package info.fekri.tmdb.ui.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.PopularMovie
import info.fekri.tmdb.model.data.Product
import info.fekri.tmdb.model.repository.ProductRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val productRepository: ProductRepository,
    isNetConnected: Boolean
) : ViewModel() {
    val dataProducts = mutableStateOf<List<Product>>(listOf())
    val dataAds = mutableStateOf<List<PopularMovie>>(listOf())
    val showProgress = mutableStateOf(false)

    init {
        refreshAllDataFromNet(isNetConnected)
    }

    private fun refreshAllDataFromNet(isNetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isNetConnected)
                showProgress.value = true

            val newDataProducts = async { productRepository.getAllProducts(isNetConnected) }
            val newDataPopular = async { productRepository.getAllPopular(isNetConnected) }

            updateData(newDataProducts.await(), newDataPopular.await())

            showProgress.value = false

        }

    }

    private fun updateData(products: List<Product>, populars: List<PopularMovie>) {
        dataProducts.value = products
        dataAds.value = populars
    }

}
package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.PopularMovie
import info.fekri.tmdb.model.data.Product
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.db.ProductDao
import info.fekri.tmdb.model.net.ApiService

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getAllProducts(isNetConnected: Boolean): List<Product> {
        if (isNetConnected) {
            // get data from internet
            val productFromServer = apiService.getAllProducts()
            productDao.insertOrUpdate(productFromServer.products)

            return productFromServer.products
        } else {
            // get data from cache (local)
            return productDao.getAll()
        }
        return listOf()
    }

    override suspend fun getAllPopular(isNetConnected: Boolean): List<PopularMovie> {

        if (isNetConnected) {
            val result = apiService.getPopularMovie()
            return result.populars
        }

        return listOf()
    }

}
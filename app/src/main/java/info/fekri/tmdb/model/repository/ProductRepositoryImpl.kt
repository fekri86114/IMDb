package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.PopularMovie
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.db.ProductDao
import info.fekri.tmdb.model.net.ApiService
import info.fekri.tmdb.util.coroutineExceptionHandler

class ProductRepositoryImpl(
    private val apiService: ApiService,
    private val productDao: ProductDao
    ): ProductRepository {

    override suspend fun getAllProducts(): List<QueryResult> {
        TODO("Not yet implemented.")
    }

    override suspend fun getAllPopular(): List<PopularMovie> {
        TODO("Not yet implemented.")
    }

}
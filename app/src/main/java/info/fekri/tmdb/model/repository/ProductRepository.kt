package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.PopularMovie
import info.fekri.tmdb.model.data.Product
import info.fekri.tmdb.model.data.QueryResult

interface ProductRepository {

    suspend fun getAllProducts(isNetConnected: Boolean): List<Product>

    suspend fun getAllPopular(isNetConnected: Boolean): List<PopularMovie>

}
package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.PopularMovie
import info.fekri.tmdb.model.data.QueryResult

interface ProductRepository {

    suspend fun getAllProducts(): List<QueryResult>

    suspend fun getAllPopular(): List<PopularMovie>

}
package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.MovieByQuery

interface MainScreenRepository {

    suspend fun getAllCategories(query: String): List<MovieByQuery>

}
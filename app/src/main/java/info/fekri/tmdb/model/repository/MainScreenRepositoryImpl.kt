package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.MovieByQuery
import info.fekri.tmdb.model.net.ApiService

class MainScreenRepositoryImpl(private val apiService: ApiService, private val isInternetConnected: Boolean): MainScreenRepository {
    override suspend fun getAllCategories(query: String): List<MovieByQuery> {
        if (isInternetConnected) {

        } else {

        }

        return listOf()
    }

}
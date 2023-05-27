package info.fekri.tmdb.model.repository.main

import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.net.ApiService

class MainRepositoryImpl(
    private val apiService: ApiService
) : MainRepository {

    override suspend fun getActionMovies(isInternetConnected: Boolean): List<QueryResult> {
        if (isInternetConnected) {
            return apiService.getMovieByQuery(query = "Action").results
        }

        return listOf()
    }

    override suspend fun getAnimationMovies(isInternetConnected: Boolean): List<QueryResult> {
        if (isInternetConnected) {
            return apiService.getMovieByQuery(query = "Animation").results
        }

        return listOf()
    }

    override suspend fun getMysteryMovies(isInternetConnected: Boolean): List<QueryResult> {
        if (isInternetConnected) {
            return apiService.getMovieByQuery(query = "Mystery").results
        }

        return listOf()
    }

}
package info.fekri.tmdb.model.repository.main

import android.content.SharedPreferences
import info.fekri.tmdb.model.data.MovieById
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.net.ApiService
import info.fekri.tmdb.util.CACHE_MOVIE_ID
import info.fekri.tmdb.util.CACHE_MOVIE_IMG_BACK_URL
import info.fekri.tmdb.util.CACHE_MOVIE_LANGUAGE
import info.fekri.tmdb.util.CACHE_MOVIE_OVERVIEW
import info.fekri.tmdb.util.CACHE_MOVIE_POPULARITY
import info.fekri.tmdb.util.CACHE_MOVIE_RELEASE_DATE
import info.fekri.tmdb.util.CACHE_MOVIE_TITLE

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
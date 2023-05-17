package info.fekri.tmdb.model.repository.main

import info.fekri.tmdb.model.data.MovieById
import info.fekri.tmdb.model.data.QueryResult

interface MainRepository {

    suspend fun getActionMovies(isInternetConnected: Boolean) : List<QueryResult>

    suspend fun getAnimationMovies(isInternetConnected: Boolean) : List<QueryResult>

    suspend fun getMysteryMovies(isInternetConnected: Boolean) : List<QueryResult>

    suspend fun getMovieById(id: Int): MovieById

}

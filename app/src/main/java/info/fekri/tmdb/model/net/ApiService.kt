package info.fekri.tmdb.model.net

import info.fekri.tmdb.model.data.*

interface ApiService {

    suspend fun getPopMovies(): PopularMovie

}

fun createApiService() {

}

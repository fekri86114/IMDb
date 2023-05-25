package info.fekri.tmdb.model.repository.detail

import info.fekri.tmdb.model.data.MovieById
import info.fekri.tmdb.model.net.ApiService

class DetailRepositoryImpl(private val apiService: ApiService): DetailRepository {
    override suspend fun getMovieById(id: Int): MovieById {
        return apiService.getMovieById(id)
    }
}
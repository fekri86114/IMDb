package info.fekri.tmdb.model.repository.detail

import info.fekri.tmdb.model.data.MovieById

interface DetailRepository {

    suspend fun getMovieById(id: Int): MovieById

}
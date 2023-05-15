package info.fekri.tmdb.repository

interface MovieRepository {

    suspend fun getMovieData()

}
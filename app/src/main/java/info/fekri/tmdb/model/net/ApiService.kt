package info.fekri.tmdb.model.net

import info.fekri.tmdb.model.data.*
import info.fekri.tmdb.util.API_KEY
import info.fekri.tmdb.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // https://api.themoviedb.org/3/search/movie?api_key=<API_KEY>&query=Jack+Reacher --> by query
    @GET("search/movie")
    suspend fun getMovieByQuery(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String
    ): MovieByQueryResponse

    @GET("search/movie")
    suspend fun getAllProducts(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Fantasy"
    ): ProductResponse

    // https://api.themoviedb.org/3/movie/popular?api_key=<API_KEY>
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String = API_KEY
    ): PopularMovieResponse

}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}

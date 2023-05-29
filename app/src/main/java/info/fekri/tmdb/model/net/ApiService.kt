package info.fekri.tmdb.model.net

import info.fekri.tmdb.model.data.*
import info.fekri.tmdb.util.API_KEY
import info.fekri.tmdb.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // https://api.themoviedb.org/3/search/movie?api_key=API_KEY&query=Jack+Reacher --> by query
    @GET("search/movie")
    suspend fun getMovieByQuery(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String
    ): MovieByQuery

    // https://api.themoviedb.org/3/movie/<MOVIE_ID>?api_key=<API_KEY> -> by id
//    @GET("movie/{movie_id}")
//    suspend fun getMovieById(@Path("movie_id") movieId: Int): MovieById

}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}

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

    // https://api.themoviedb.org/3/search/movie?api_key=<API_KEY>&query=Jack+Reacher --> query
    @GET("search/movie")
    suspend fun getMovieByQuery(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String
    ): MovieByQueryResponse

    @GET("search/movie")
    suspend fun getAllActions(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Action"
    ): ActionResponse

    @GET("search/movie")
    suspend fun getAllFantasy(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Fantasy"
    ): FantasyResponse

    @GET("movie/popular")
    suspend fun getAllPopulars(
        @Query("api_key") apiKey: String = API_KEY,
    ): PopularResponse

}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}

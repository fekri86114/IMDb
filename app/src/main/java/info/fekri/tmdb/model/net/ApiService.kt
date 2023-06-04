package info.fekri.tmdb.model.net

import info.fekri.tmdb.model.data.*
import info.fekri.tmdb.model.data.movie.ActionResponse
import info.fekri.tmdb.model.data.movie.AdventureResponse
import info.fekri.tmdb.model.data.movie.ComedyResponse
import info.fekri.tmdb.model.data.movie.DramaResponse
import info.fekri.tmdb.model.data.movie.FantasyResponse
import info.fekri.tmdb.model.data.movie.HorrorResponse
import info.fekri.tmdb.model.data.movie.MysteryResponse
import info.fekri.tmdb.model.data.movie.ScientificResponse
import info.fekri.tmdb.util.API_KEY
import info.fekri.tmdb.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{movieId}") // https://themoviedb.org/3/movie/tt0078732?api_key=f882fe7e318f300420b26bdf6e0db009
    suspend fun getMovieById(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieId

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

    @GET("search/movie")
    suspend fun getAllComedy(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Comedy"
    ): ComedyResponse

    @GET("search/movie")
    suspend fun getAllDrama(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Drama"
    ): DramaResponse

    @GET("search/movie")
    suspend fun getAllHorror(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Horror"
    ): HorrorResponse

    @GET("search/movie")
    suspend fun getAllMystery(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Mystery"
    ): MysteryResponse

    @GET("search/movie")
    suspend fun getAllAdventure(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Adventure"
    ): AdventureResponse

    @GET("search/movie")
    suspend fun getAllScientific(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "Science Fiction"
    ): ScientificResponse

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

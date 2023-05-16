package info.fekri.tmdb.model.net

import info.fekri.tmdb.model.data.Movie
import info.fekri.tmdb.util.API_KEY
import info.fekri.tmdb.util.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("trending/movie/day")
    suspend fun getMovieData(
        @Path("language") language: String = "en-US",
        @Path("api_key") apiKey: String = API_KEY
    ): Movie

}

fun createApiService(): ApiService {

    val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor {

            val oldRequest = it.request()

            val newRequest = oldRequest.newBuilder()
            newRequest.addHeader("Accept", "application/json")
            newRequest.method(oldRequest.method, oldRequest.body)

            return@addInterceptor it.proceed(newRequest.build())
        }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}
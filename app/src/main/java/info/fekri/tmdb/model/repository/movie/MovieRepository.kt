package info.fekri.tmdb.model.repository.movie

import info.fekri.tmdb.model.data.MovieId
import info.fekri.tmdb.model.data.Popular
import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.data.movie.Action
import info.fekri.tmdb.model.data.movie.Adventure
import info.fekri.tmdb.model.data.movie.Comedy
import info.fekri.tmdb.model.data.movie.Drama
import info.fekri.tmdb.model.data.movie.Fantasy
import info.fekri.tmdb.model.data.movie.Horror
import info.fekri.tmdb.model.data.movie.Mystery
import info.fekri.tmdb.model.data.movie.Scientific

interface MovieRepository {

    suspend fun getAllActions(isNetConnected: Boolean): List<Action>
    suspend fun getAllFantasies(isNetConnected: Boolean): List<Fantasy>
    suspend fun getAllPops(): List<Popular>

    suspend fun getAllComedies(isNetConnected: Boolean): List<Comedy>
    suspend fun getAllDramas(isNetConnected: Boolean): List<Drama>
    suspend fun getAllHorrors(isNetConnected: Boolean): List<Horror>

    suspend fun getAllMysteries(isNetConnected: Boolean): List<Mystery>
    suspend fun getAllAdventures(isNetConnected: Boolean): List<Adventure>
    suspend fun getAllScientific(isNetConnected: Boolean): List<Scientific>

    suspend fun getMovieById(id: Int): MovieId
    suspend fun getMovieByQuery(query: String): List<QueryResult>

}
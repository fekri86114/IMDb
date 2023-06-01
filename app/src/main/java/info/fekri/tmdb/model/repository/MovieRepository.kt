package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.*
import info.fekri.tmdb.model.data.movie.*

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

}
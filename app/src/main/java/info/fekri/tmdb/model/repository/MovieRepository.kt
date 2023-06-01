package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.*

interface MovieRepository {

    suspend fun getAllActions(isNetConnected: Boolean): List<Action>

    suspend fun getAllFantasies(isNetConnected: Boolean): List<Fantasy>

    suspend fun getAllPops(): List<PopularResponse.Popular>

}
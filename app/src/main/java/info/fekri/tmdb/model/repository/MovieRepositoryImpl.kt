package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.Action
import info.fekri.tmdb.model.data.Fantasy
import info.fekri.tmdb.model.data.PopularResponse
import info.fekri.tmdb.model.net.ApiService

class MovieRepositoryImpl(
    private val apiService: ApiService,
) : MovieRepository {

    override suspend fun getAllActions(isNetConnected: Boolean): List<Action> {

        if (isNetConnected) {
            // get data from server
            val actionFromServer = apiService.getAllActions()

            return actionFromServer.results

        } else {
            // get data from cache

        }

        return listOf()
    }

    override suspend fun getAllFantasies(isNetConnected: Boolean): List<Fantasy> {

        if (isNetConnected) {
            // get data from server
            val fantasy = apiService.getAllFantasy()

            return fantasy.results

        } else {
            // get data from cache
        }

        return listOf()
    }

    override suspend fun getAllPops(): List<PopularResponse.Popular> {
        val pops = apiService.getAllPopulars()
        return pops.populars
    }

}
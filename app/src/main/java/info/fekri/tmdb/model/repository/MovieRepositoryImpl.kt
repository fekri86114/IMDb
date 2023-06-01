package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.Popular
import info.fekri.tmdb.model.data.movie.Action
import info.fekri.tmdb.model.data.movie.ComedyResponse
import info.fekri.tmdb.model.data.movie.Fantasy
import info.fekri.tmdb.model.data.PopularResponse
import info.fekri.tmdb.model.data.movie.Adventure
import info.fekri.tmdb.model.data.movie.Comedy
import info.fekri.tmdb.model.data.movie.Drama
import info.fekri.tmdb.model.data.movie.Horror
import info.fekri.tmdb.model.data.movie.Mystery
import info.fekri.tmdb.model.data.movie.Scientific
import info.fekri.tmdb.model.net.ApiService

class MovieRepositoryImpl(
    private val apiService: ApiService,
) : MovieRepository {

    override suspend fun getAllActions(isNetConnected: Boolean): List<Action> {

        if (isNetConnected) {
            // get data from server
            val actionFromServer = apiService.getAllActions()

            return actionFromServer.results

        }

        return listOf()
    }
    override suspend fun getAllFantasies(isNetConnected: Boolean): List<Fantasy> {

        if (isNetConnected) {
            // get data from server
            val fantasy = apiService.getAllFantasy()

            return fantasy.results

        }

        return listOf()
    }
    override suspend fun getAllPops(): List<Popular> {
        val pops = apiService.getAllPopulars()
        return pops.populars
    }

    override suspend fun getAllComedies(isNetConnected: Boolean): List<Comedy> {
        if (isNetConnected) {
            val comediesFromServer = apiService.getAllComedy()
            return comediesFromServer.comedies
        }

        return listOf()
    }
    override suspend fun getAllDramas(isNetConnected: Boolean): List<Drama> {
        if (isNetConnected) {
            val dramasFromServer = apiService.getAllDrama()
            return dramasFromServer.dramas
        }

        return listOf()
    }
    override suspend fun getAllHorrors(isNetConnected: Boolean): List<Horror> {
        if (isNetConnected) {
            val horrorFromServer = apiService.getAllHorror()
            return horrorFromServer.horrors
        }

        return listOf()
    }

    override suspend fun getAllMysteries(isNetConnected: Boolean): List<Mystery> {
        if (isNetConnected) {
            val mysteryFromServer = apiService.getAllMystery()
            return mysteryFromServer.mysteries
        }

        return listOf()
    }
    override suspend fun getAllAdventures(isNetConnected: Boolean): List<Adventure> {
        if (isNetConnected) {
            val adventuresFromServer = apiService.getAllAdventure()
            return adventuresFromServer.adventures
        }

        return listOf()
    }
    override suspend fun getAllScientific(isNetConnected: Boolean): List<Scientific> {
        if (isNetConnected) {
            val scientificFromServer = apiService.getAllScientific()
            return scientificFromServer.scientific
        }

        return listOf()
    }

}
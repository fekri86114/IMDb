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
import info.fekri.tmdb.model.db.dao.ActionDao
import info.fekri.tmdb.model.db.dao.AdventureDao
import info.fekri.tmdb.model.db.dao.ComedyDao
import info.fekri.tmdb.model.db.dao.DramaDao
import info.fekri.tmdb.model.db.dao.FantasyDao
import info.fekri.tmdb.model.db.dao.HorrorDao
import info.fekri.tmdb.model.db.dao.MysteryDao
import info.fekri.tmdb.model.db.dao.ScientificDao
import info.fekri.tmdb.model.net.ApiService

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val actionDao: ActionDao,
    private val adventureDao: AdventureDao,
    private val comedyDao: ComedyDao,
    private val dramaDao: DramaDao,
    private val fantasyDao: FantasyDao,
    private val horrorDao: HorrorDao,
    private val mysteryDao: MysteryDao,
    private val scientificDao: ScientificDao
) : MovieRepository {

    override suspend fun getAllActions(isNetConnected: Boolean): List<Action> {

        if (isNetConnected) {
            // get data from server
            val actionFromServer = apiService.getAllActions()
            actionDao.insertOrUpdate(actionFromServer.results)

            return actionFromServer.results

        } else {
            // get data from cache (room database)
            return actionDao.getAllActions()
        }

        return listOf()
    }
    override suspend fun getAllFantasies(isNetConnected: Boolean): List<Fantasy> {

        if (isNetConnected) {
            // get data from server
            val fantasy = apiService.getAllFantasy()
            fantasyDao.insertOrUpdate(fantasy.results)

            return fantasy.results

        } else {
            return fantasyDao.getAllFantasies()
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
            comedyDao.insertOrUpdate(comediesFromServer.comedies)

            return comediesFromServer.comedies
        } else {
            return comedyDao.getAllComedies()
        }

        return listOf()
    }
    override suspend fun getAllDramas(isNetConnected: Boolean): List<Drama> {
        if (isNetConnected) {
            val dramasFromServer = apiService.getAllDrama()
            dramaDao.insertOrUpdate(dramasFromServer.dramas)

            return dramasFromServer.dramas
        } else {
            return dramaDao.getAllDramas()
        }

        return listOf()
    }
    override suspend fun getAllHorrors(isNetConnected: Boolean): List<Horror> {
        if (isNetConnected) {
            val horrorFromServer = apiService.getAllHorror()
            horrorDao.insertOrUpdate(horrorFromServer.horrors)

            return horrorFromServer.horrors
        } else {
            return horrorDao.getAllHorrors()
        }

        return listOf()
    }

    override suspend fun getAllMysteries(isNetConnected: Boolean): List<Mystery> {
        if (isNetConnected) {
            val mysteryFromServer = apiService.getAllMystery()
            mysteryDao.insertOrUpdate(mysteryFromServer.mysteries)

            return mysteryFromServer.mysteries
        } else {
            return mysteryDao.getAllMysteries()
        }

        return listOf()
    }
    override suspend fun getAllAdventures(isNetConnected: Boolean): List<Adventure> {
        if (isNetConnected) {
            val adventuresFromServer = apiService.getAllAdventure()
            adventureDao.insertOrUpdate(adventuresFromServer.adventures)

            return adventuresFromServer.adventures
        } else {
            return adventureDao.getAllAdvents()
        }

        return listOf()
    }
    override suspend fun getAllScientific(isNetConnected: Boolean): List<Scientific> {
        if (isNetConnected) {
            val scientificFromServer = apiService.getAllScientific()
            scientificDao.insertOrUpdate(scientificFromServer.scientific)

            return scientificFromServer.scientific
        } else {
            return scientificDao.getAllScientific()
        }

        return listOf()
    }

}
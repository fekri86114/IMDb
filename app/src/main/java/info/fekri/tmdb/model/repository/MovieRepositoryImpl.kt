package info.fekri.tmdb.model.repository

import info.fekri.tmdb.model.data.*
import info.fekri.tmdb.model.db.dao.*
import info.fekri.tmdb.model.net.ApiService

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val actionDao: ActionDao,
    private val fantasyDao: FantasyDao,
    private val adventureDao: AdventureDao
): MovieRepository {
    override suspend fun getAllActions(isNetConnected: Boolean): List<Action> {

        if (isNetConnected) {
            // get data from server
            val actionFromServer =apiService.getAllActions()
            actionDao.insertOrUpdateAction(actionFromServer.actions)

            return actionFromServer.actions

        } else {
            // get data from cache
            return actionDao.getAllAction()
        }

        return listOf()
    }

    override suspend fun getAllFantasies(isNetConnected: Boolean): List<Fantasy> {

        if (isNetConnected) {
            // get data from server
            val fantasy = apiService.getAllFantasy()
            fantasyDao.insertOrUpdateFantasy(fantasy.fantasies)

            return fantasy.fantasies
        } else {
            // get data from cache
            return fantasyDao.getAllFantasy()
        }

        return listOf()
    }

    override suspend fun getAllAdventure(isNetConnected: Boolean): List<Adventure> {

        if (isNetConnected) {
            val advent = apiService.getAllAdventure()
            adventureDao.insertOrUpdateAdventure(advent.adventures)

            return advent.adventures
        } else {
            return adventureDao.getAllAdventure()
        }

        return listOf()
    }
}
package info.fekri.tmdb.model.repository.search

import info.fekri.tmdb.model.data.QueryResult

interface SearchRepository {

    suspend fun getMusicBySearch(search: String): List<QueryResult>

}
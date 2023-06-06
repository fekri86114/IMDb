package info.fekri.tmdb.model.repository.search

import info.fekri.tmdb.model.data.QueryResult
import info.fekri.tmdb.model.net.ApiService

class SearchRepositoryImpl(
    private val apiService: ApiService
): SearchRepository {

    override suspend fun getMusicBySearch(search: String): List<QueryResult> {
        val dataSearch = apiService.getMovieByQuery(query = search)
        return dataSearch.results
    }

}
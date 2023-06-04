package info.fekri.tmdb.ui.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.model.data.Popular
import info.fekri.tmdb.model.data.movie.Action
import info.fekri.tmdb.model.data.movie.Adventure
import info.fekri.tmdb.model.data.movie.Comedy
import info.fekri.tmdb.model.data.movie.Drama
import info.fekri.tmdb.model.data.movie.Fantasy
import info.fekri.tmdb.model.data.movie.Horror
import info.fekri.tmdb.model.data.movie.Mystery
import info.fekri.tmdb.model.data.movie.Scientific
import info.fekri.tmdb.model.repository.MovieRepository
import info.fekri.tmdb.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val movieRepository: MovieRepository,
    isNetConnected: Boolean
) : ViewModel() {
    val dataActions = mutableStateOf<List<Action>>(listOf())
    val dataFantasies = mutableStateOf<List<Fantasy>>(listOf())
    val dataPopulars = mutableStateOf<List<Popular>>(listOf())

    val dataComedies = mutableStateOf<List<Comedy>>(listOf())
    val dataDramas = mutableStateOf<List<Drama>>(listOf())
    val dataHorrors = mutableStateOf<List<Horror>>(listOf())

    val dataMysteries = mutableStateOf<List<Mystery>>(listOf())
    val dataAdventures = mutableStateOf<List<Adventure>>(listOf())
    val dataScientific = mutableStateOf<List<Scientific>>(listOf())

    val showProgress = mutableStateOf(false)
    val showMoreProgress = mutableStateOf(false)
    val showLoadMoreButton = mutableStateOf(true)

    init {
        refreshAllDataFromNet(isNetConnected)
    }

    private fun refreshAllDataFromNet(isNetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {
            if (isNetConnected) {
                showProgress.value = true
                delay(1200)

                val dataActionToSet = async { movieRepository.getAllActions(isNetConnected) }
                val dataFantasiesToSet = async { movieRepository.getAllFantasies(isNetConnected) }
                val dataPops = async { movieRepository.getAllPops() }

                val dataComedyToSet = async { movieRepository.getAllComedies(isNetConnected) }
                val dataDramaToSet = async { movieRepository.getAllDramas(isNetConnected) }
                val dataHorrorToSet = async { movieRepository.getAllHorrors(isNetConnected) }

                updateDataMovies(
                    dataActionToSet.await(),
                    dataFantasiesToSet.await(),
                    dataComedyToSet.await(),
                    dataDramaToSet.await(),
                    dataHorrorToSet.await(),
                    dataPops.await()
                )

                showProgress.value = false
            }
        }

    }

    private fun updateDataMovies(
        actionsData: List<Action>,
        fantasies: List<Fantasy>,
        comedies: List<Comedy>,
        dramas: List<Drama>,
        horrors: List<Horror>,
        pops: List<Popular>
    ) {
        dataActions.value = actionsData
        dataFantasies.value = fantasies
        dataPopulars.value = pops

        dataComedies.value = comedies
        dataDramas.value = dramas
        dataHorrors.value = horrors
    }

    fun loadMoreData(isNetConnected: Boolean) {
        if (isNetConnected) {
            viewModelScope.launch(coroutineExceptionHandler) {
                showMoreProgress.value = true
                delay(1200)

                val dataMysteryToSet = async { movieRepository.getAllMysteries(isNetConnected) }
                val dataAdventureToSet = async { movieRepository.getAllAdventures(isNetConnected) }
                val dataScientificToSet = async { movieRepository.getAllScientific(isNetConnected) }

                updateMoreData(
                    dataMysteryToSet.await(),
                    dataAdventureToSet.await(),
                    dataScientificToSet.await()
                )

                showLoadMoreButton.value = false
                showMoreProgress.value = false
            }
        }
    }

    private fun updateMoreData(
        mysteries: List<Mystery>,
        adventures: List<Adventure>,
        scientific: List<Scientific>
    ) {
        dataMysteries.value = mysteries
        dataAdventures.value = adventures
        dataScientific.value = scientific
    }

}
package info.fekri.tmdb.util

import info.fekri.tmdb.model.data.MovieById

const val API_KEY = "f882fe7e318f300420b26bdf6e0db009"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

const val KEY_MOVIE_ITEM = "onMovieItemClicked"

val EMPTY_MOVIE_BY_ID = MovieById(
    "", 0, "", 0, "", "", "", "", 0.0, "", "", 0, 0, ""
)

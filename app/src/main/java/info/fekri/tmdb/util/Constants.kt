package info.fekri.tmdb.util

import info.fekri.tmdb.model.data.MovieById

const val API_KEY = "f882fe7e318f300420b26bdf6e0db009"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

const val KEY_MOVIE_ARG = "movieItemClickedArg"

val EMPTY_MOVIE_BY_ID = MovieById(
    "", 0, "", 0, "", "", "", "", 0.0, "", "", 0, 0, ""
)

// shared-preferences data -->
const val CACHE_MOVIE_ID = "movie_id"
const val CACHE_MOVIE_POPULARITY = "movie_popularity"
const val CACHE_MOVIE_IMG_BACK_URL = "movie_image_url"
const val CACHE_MOVIE_TITLE = "movie_title"
const val CACHE_MOVIE_OVERVIEW = "movie_overview"
const val CACHE_MOVIE_RELEASE_DATE = "movie_release_date"
const val CACHE_MOVIE_LANGUAGE = "movie_language"

package info.fekri.tmdb.util

import info.fekri.tmdb.R
import info.fekri.tmdb.model.data.MovieId
import info.fekri.tmdb.model.data.QueryResult

const val API_KEY = "f882fe7e318f300420b26bdf6e0db009"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val IP_API_KEY = "875aa28c376e8f7145686757e6b567a5"

const val KEY_MOVIE_ARG = "movieItemClickedArg"
const val KEY_MOVIE_CATEGORY = "categoryMovieId"

val EMPTY_QUERY_RESULT = QueryResult(
    "",
    0,
    "",
    "",
    "",
    0.0,
    "",
    "",
    "",
    false,
    0.0,
    0
)

val CATEGORY_LIST: List<Pair<String, Int>> = listOf(
    Pair("Action", R.drawable.img_action),
    Pair("Comedy", R.drawable.img_comedy),
    Pair("Drama", R.drawable.img_drama),
    Pair("Fantasy", R.drawable.img_fantasey),
    Pair("Horror", R.drawable.img_horror),
    Pair("Mystery", R.drawable.img_mystery),
    Pair("Adventure", R.drawable.img_adventure),
    Pair("Scientific", R.drawable.img_science_fiction)
)

val EMPTY_MOVIE_ID = MovieId(
    false,
    "",
    "",
    0,
    "",
    0,
    "",
    "",
    "",
    "",
    0.0,
    "",
    listOf(),
    "",
    0,
    0,
    "",
    "",
    "",
    false,
    0.0,
    0
)

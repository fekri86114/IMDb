package info.fekri.tmdb.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import okio.utf8Size

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("error", "Error -> ${throwable.message}")
}

fun styleLimitText(text: String, length: Int): String {
    if (text.length > length) return text.substring(0, length) + "..."
    return text
}

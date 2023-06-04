package info.fekri.tmdb.util

import android.app.Application
import android.net.http.HttpResponseCache.install
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("error", "Error -> ${throwable.message}")
}

fun styleLimitedText(text: String, length: Int): String {
    if (text.length > length) return text.substring(0, length) + "..."
    return text
}

fun stylePrice(oldPrice: String): String {

    if (oldPrice.length > 3) {
        val reversed = oldPrice.reversed()
        var newPrice = ""
        for (i in oldPrice.indices) {
            newPrice += reversed[i]
            if ((i+1) % 3 == 0) {
                newPrice += ','
            }
        }
        val readyToGo = newPrice.reversed()

        // if you don't use it, when the number is (for example) 532,234
        // it will be like this: ,532,234
        // so we need it :-)
        if (readyToGo.first() == ',') {
            return readyToGo.substring(1) + "$"
        }

        return "$readyToGo$"
    }

    return "$oldPrice$"
}



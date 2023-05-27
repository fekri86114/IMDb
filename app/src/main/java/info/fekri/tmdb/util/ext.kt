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

fun isUserFromIran(ipAddress: String): Boolean {

    val url = "https://api.ipapi.com/$ipAddress?access_key=$IP_API_KEY"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).execute().use { response ->
        val jsonString = response.body?.string()
        val jsonObject = JSONObject(jsonString!!)
        val country = jsonObject.getString("country_code2")

        return country == "IR"
    }

}



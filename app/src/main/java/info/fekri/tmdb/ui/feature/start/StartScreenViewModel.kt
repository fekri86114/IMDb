package info.fekri.tmdb.ui.feature.start

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.fekri.tmdb.util.coroutineExceptionHandler
import info.fekri.tmdb.util.isUserFromIran
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class StartScreenViewModel: ViewModel() {
    var isUserFromIran = mutableStateOf(false)
        private set
    var userIpAddress = mutableStateOf("")
        private set

    fun checkUserLocation(ipAddress: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            isUserFromIran.value = isUserFromIran(ipAddress)
        }
    }

    fun fetchUserIpAddress() {
        viewModelScope.launch(coroutineExceptionHandler) {
            userIpAddress.value = retrieveUserIpAddress()
        }
    }

    private suspend fun retrieveUserIpAddress(): String {
        return withContext(Dispatchers.IO) {
            val url = "https://api.ipify.org?format=json"

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString!!)
                val ipAddress = jsonObject.getString("ip")

                ipAddress
            }
        }
    }

}
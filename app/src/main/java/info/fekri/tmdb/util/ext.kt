package info.fekri.tmdb.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler
import java.io.File

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

fun downloadImageNew(filename: String, downloadUrlOfImage: String, context: Context) {
    try {
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val downloadUri = Uri.parse(downloadUrlOfImage)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            .setMimeType("image/jpeg") // Your file type.
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                File.separator + filename + ".jpg"
            )
        dm!!.enqueue(request)
        Toast.makeText(context, "Image download started.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Image download failed.", Toast.LENGTH_SHORT).show()
    }
}

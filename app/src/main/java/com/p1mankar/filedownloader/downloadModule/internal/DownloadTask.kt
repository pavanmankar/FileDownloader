package com.library.internal

import com.library.httpclient.HttpClient
import com.p1mankar.filedownloader.downloadModule.internal.DownloadRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DownloadTask(private val req: DownloadRequest, private val httpClient: HttpClient) {

    suspend fun run(
        onStart: () -> Unit = {},
        onProgress: (value: Int) -> Unit = { _ -> },
        onPause: () -> Unit = {},
        onCompleted: () -> Unit = {},
        onError: (error: String) -> Unit = { _ -> }
    ) {
        withContext(Dispatchers.IO) {
            // dummy code for downloading the file

            onStart()

            // use of HttpClient
            httpClient.connect(req)

            // simulate read data from internet
            for (i in 1..100) {
                delay(100)
                onProgress(i)
            }

            onCompleted()

        }
    }

}
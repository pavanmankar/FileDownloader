package com.library.internal

import com.library.httpclient.HttpClient
import com.p1mankar.filedownloader.downloadModule.internal.DownloadRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DownloadDispatcher(private val httpClient: HttpClient) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun enqueue(req: DownloadRequest): Int {
        val job = scope.launch {
            execute(req)
        }
        req.job = job
        return req.downloadId
    }

    private suspend fun execute(request: DownloadRequest) {
        DownloadTask(request, httpClient).run(
            onStart = {
                executeOnMainThread { request.onStart() }
            },
            onProgress = {
                executeOnMainThread { request.onProgress(it) }
            },
            onPause = {
                executeOnMainThread { request.onPause() }
            },
            onCompleted = {
                executeOnMainThread { request.onCompleted() }
            },
            onError = {
                executeOnMainThread { request.onError(it) }
            }
        )
    }

    private fun executeOnMainThread(block: () -> Unit) {
        scope.launch {
            block()
        }
    }

    fun cancel(req: DownloadRequest) {
        req.job.cancel()
    }

    fun cancelAll() {
        scope.cancel()
    }

}
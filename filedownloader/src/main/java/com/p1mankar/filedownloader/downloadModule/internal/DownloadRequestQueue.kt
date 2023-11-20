package com.library.internal

import com.p1mankar.filedownloader.downloadModule.internal.DownloadRequest

class DownloadRequestQueue(private val dispatcher: DownloadDispatcher) {

    private val idRequestMap: HashMap<Int, DownloadRequest> = hashMapOf()

    fun enqueue(request: DownloadRequest): Int {
        idRequestMap[request.downloadId] = request
        return dispatcher.enqueue(request)
    }

    fun pause(id: Int) {
        idRequestMap[id]?.let {
            dispatcher.cancel(it)
        }
    }

    fun resume(id: Int) {
        idRequestMap[id]?.let {
            dispatcher.enqueue(it)
        }
    }

    fun cancel(id: Int) {
        idRequestMap[id]?.let {
            dispatcher.cancel(it)
        }
        idRequestMap.remove(id)
    }

    fun cancel(tag: String) {
        val requestsWithTag = idRequestMap.values.filter {
            it.tag == tag
        }
        for (req in requestsWithTag) {
            cancel(req.downloadId)
        }
    }

    fun cancelAll() {
        idRequestMap.clear()
        dispatcher.cancelAll()
    }

}
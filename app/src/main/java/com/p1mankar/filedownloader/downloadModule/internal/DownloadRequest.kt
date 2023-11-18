package com.p1mankar.filedownloader.downloadModule.internal

import com.p1mankar.filedownloader.downloadModule.utils.getUniqueId
import kotlinx.coroutines.Job


class DownloadRequest private constructor(
    internal var url: String,
    internal val tag: String?,
    internal val dirPath: String,
    internal val downloadId: Int,
    internal val fileName: String,
    internal var readTimeOut: Int,
    internal var connectTimeOut: Int,
) {

    internal var totalBytes: Long = 0
    internal var downloadedBytes: Long = 0
    internal lateinit var job: Job
    internal lateinit var onStart: () -> Unit
    internal lateinit var onProgress: (value: Int) -> Unit
    internal lateinit var onPause: () -> Unit
    internal lateinit var onCompleted: () -> Unit
    internal lateinit var onError: (error: String) -> Unit

    data class Builder(
        private val url: String,
        private val dirPath: String,
        private val fileName: String
    ) {

        private var tag: String? = null
        private var readTimeOut: Int = 0
        private var connectTimeOut: Int = 0

        fun tag(tag: String) = apply {
            this.tag = tag
        }

        fun readTimeout(timeout: Int) = apply {
            this.readTimeOut = timeout
        }

        fun connectTimeout(timeout: Int) = apply {
            this.connectTimeOut = timeout
        }

        fun build(): DownloadRequest {
            return DownloadRequest(
                url = url,
                tag = tag,
                dirPath = dirPath,
                downloadId = getUniqueId(url, dirPath, fileName),
                fileName = fileName,
                readTimeOut = readTimeOut,
                connectTimeOut = connectTimeOut,
            )
        }
    }

}
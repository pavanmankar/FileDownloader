package com.library.httpclient

import com.p1mankar.filedownloader.downloadModule.internal.DownloadRequest
import java.io.IOException
import java.io.InputStream

interface HttpClient {

    fun connect(req: DownloadRequest)

    @Throws(IOException::class)
    fun getResponseCode(): Int

    @Throws(IOException::class)
    fun getInputStream(): InputStream?

    fun getContentLength(): Long

    @Throws(IOException::class)
    fun getErrorStream(): InputStream?

}
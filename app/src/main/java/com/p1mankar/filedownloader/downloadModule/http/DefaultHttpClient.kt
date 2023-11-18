package com.p1mankar.filedownloader.downloadModule.http

import com.library.httpclient.HttpClient
import com.p1mankar.filedownloader.downloadModule.internal.DownloadRequest
import com.p1mankar.filedownloader.downloadModule.utils.Constants
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


class DefaultHttpClient : HttpClient {

    var connection: URLConnection? = null

    override fun connect(request: DownloadRequest) {
        connection = URL(request.url).openConnection()
        connection?.readTimeout = request.readTimeOut
        connection?.connectTimeout = request.connectTimeOut
        val range = "bytes=${request.downloadedBytes}-"
        connection?.setRequestProperty(Constants.RANGE, range)
        connection?.connect()
    }

    override fun getResponseCode(): Int {
        TODO("Not yet implemented")
    }

    override fun getInputStream(): InputStream? {
        TODO("Not yet implemented")
    }

    override fun getContentLength(): Long {
        TODO("Not yet implemented")
    }

    override fun getErrorStream(): InputStream? {
        TODO("Not yet implemented")
    }

}
package com.library

import com.library.httpclient.HttpClient
import com.p1mankar.filedownloader.downloadModule.http.DefaultHttpClient
import com.p1mankar.filedownloader.downloadModule.utils.Constants

data class DownloaderConfig(
    val httpClient: HttpClient = DefaultHttpClient(),
    val connectTimeOut: Int = Constants.DEFAULT_CONNECT_TIMEOUT_MILLS,
    val readTimeOut: Int = Constants.DEFAULT_READ_TIMEOUT_MILLS
)
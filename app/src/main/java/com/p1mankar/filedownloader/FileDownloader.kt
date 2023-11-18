package com.p1mankar.filedownloader

import android.app.Application
import com.library.Downloader

class FileDownloader : Application() {

    lateinit var downloader: Downloader

    override fun onCreate() {
        super.onCreate()
        downloader = Downloader.create()

    }
}
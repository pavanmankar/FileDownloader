package com.p1mankar.filedownloader

import android.app.Application
import com.library.Downloader

class MyApplication : Application() {

    lateinit var downloader: Downloader

    override fun onCreate() {
        super.onCreate()
        downloader = Downloader.create()

    }
}
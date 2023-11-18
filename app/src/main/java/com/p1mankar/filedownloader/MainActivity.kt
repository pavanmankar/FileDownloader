package com.p1mankar.filedownloader

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.library.Downloader
import com.p1mankar.filedownloader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    lateinit var dirPath: String
    lateinit var downloader: Downloader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        downloader = Downloader.create()
        dirPath = Environment.getExternalStorageDirectory().path + "/Download"
        setOnClickListener()
    }

    private fun setOnClickListener() {
        val request = downloader.newReqBuilder(
            "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4",
            dirPath,
            "bunny.mp4",
        ).tag(TAG + "1").build()
        binding.startCancelButton1.setOnClickListener {
            downloader.enqueue(request, onStart = {
                binding.status1.text = "Started"
                Log.d(TAG, "On Start")
            }, onProgress = {
                binding.status1.text = "In Progress"
                binding.progressBar1.progress = it
                binding.progressText1.text = "$it%"
                Log.d(TAG, it.toString())
            }, onPause = {
                binding.status1.text = "Paused"
                Log.d(TAG, "On Pause")
            }, onCompleted = {
                binding.status1.text = "Completed"
                Log.d(TAG, "On Complete")
            }, onError = {
                binding.status1.text = "Error : $it"
                binding.resumePauseButton1.visibility = View.GONE
                binding.progressBar1.progress = 0
                binding.progressText1.text = "0%"
                Log.d(TAG, it)
            })

        }
    }
}
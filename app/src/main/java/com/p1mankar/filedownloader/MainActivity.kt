package com.p1mankar.filedownloader

import android.app.Application
import android.content.Context
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

        downloader = (application as MyApplication).downloader
        dirPath = Environment.getExternalStorageDirectory().path + "/Download"
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.startCancelButton1.setOnClickListener {
            var downloadId1 = 0
            if (binding.startCancelButton1.text.equals("Start")) {
                val request = downloader.newReqBuilder(
                    "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4",
                    dirPath,
                    "bunny.mp4",
                ).tag(TAG + "1").build()
                downloadId1 = downloader.enqueue(request, onStart = {
                    binding.status1.text = "Started"
                    binding.startCancelButton1.text = "Cancel"
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
                    binding.startCancelButton1.text = "Completed"
                }, onError = {
                    binding.status1.text = "Error : $it"
                    binding.resumePauseButton1.visibility = View.GONE
                    binding.progressBar1.progress = 0
                    binding.progressText1.text = "0%"
                    Log.d(TAG, it)
                })
            } else if (binding.startCancelButton1.text.equals("Cancel")) {
                downloader.cancel(TAG + "1")
                binding.startCancelButton1.text = "Start"
            }

        }

        binding.startCancelButton2.setOnClickListener {
            var downloadId2 = 0
            if (binding.startCancelButton2.text.equals("Start")) {
                val request1 = downloader.newReqBuilder(
                    "https://media.giphy.com/media/Bk0CW5frw4qfS/giphy.gif",
                    dirPath,
                    "docu.gif",
                ).tag(TAG + "1").build()
                downloadId2 = downloader.enqueue(request1, onStart = {
                    binding.status2.text = "Started"
                    binding.startCancelButton2.text = "Cancel"
                    Log.d(TAG, "On Start")
                }, onProgress = {
                    binding.status2.text = "In Progress"
                    binding.progressBar2.progress = it
                    binding.progressText2.text = "$it%"
                    Log.d(TAG, it.toString())
                }, onPause = {
                    binding.status2.text = "Paused"
                    Log.d(TAG, "On Pause")
                }, onCompleted = {
                    binding.status2.text = "Completed"
                    Log.d(TAG, "On Complete")
                    binding.startCancelButton2.text = "Completed"
                }, onError = {
                    binding.status2.text = "Error : $it"
                    binding.resumePauseButton2.visibility = View.GONE
                    binding.progressBar2.progress = 0
                    binding.progressText2.text = "0%"
                    Log.d(TAG, it)
                })
            } else if (binding.startCancelButton2.text.equals("Cancel")) {
                downloader.cancel(downloadId2)
                binding.startCancelButton2.text = "Start"
            }

        }
    }
}
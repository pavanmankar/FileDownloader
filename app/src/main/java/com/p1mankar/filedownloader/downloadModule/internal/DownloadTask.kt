package com.library.internal

import com.library.httpclient.HttpClient
import com.p1mankar.filedownloader.downloadModule.internal.DownloadRequest
import com.p1mankar.filedownloader.downloadModule.utils.*
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.io.InputStream

class DownloadTask(private val req: DownloadRequest, private val httpClient: HttpClient) {

    private var responseCode = 0
    private var totalBytes: Long = 0
    private var inputStream: InputStream? = null
    private lateinit var outputStream: FileDownloadOutputStream

    private var tempPath: String = ""
    private var isResumeSupported = true


    companion object {
        private const val TIME_GAP_FOR_SYNC: Long = 2000
        private const val MIN_BYTES_FOR_SYNC: Long = 65536
        private const val BUFFER_SIZE = 1024 * 4
    }

    suspend fun run(
        onStart: () -> Unit = {},
        onProgress: (value: Int) -> Unit = { _ -> },
        onPause: () -> Unit = {},
        onCompleted: () -> Unit = {},
        onError: (error: String) -> Unit = { _ -> }
    ) {
        withContext(Dispatchers.IO) {
            try {

                tempPath = getTempPath(req.dirPath, req.fileName)
                var file = File(tempPath)

                req.status = Status.RUNNING

                onStart()

                httpClient.connect(req)

                responseCode = httpClient.getResponseCode()

                totalBytes = req.totalBytes


                if (totalBytes == 0L) {
                    totalBytes = httpClient.getContentLength()
                    req.totalBytes = (totalBytes)
                }

                inputStream = httpClient.getInputStream()
                if (inputStream == null) {
                    return@withContext
                }

                val buff = ByteArray(BUFFER_SIZE)

                if (!file.exists()) {
                    val parentFile = file.parentFile
                    if (parentFile != null && !parentFile.exists()) {
                        if (parentFile.mkdirs()) {
                            file.createNewFile()
                        }
                    } else {
                        file.createNewFile()
                    }
                }

                this@DownloadTask.outputStream = FileDownloadRandomAccessFile.create(file)

                do {
                    val byteCount = inputStream!!.read(buff, 0, BUFFER_SIZE)
                    if (byteCount == -1) {
                        break
                    }

                    outputStream.write(buff, 0, byteCount)
                    req.downloadedBytes = req.downloadedBytes + byteCount

                    var progress = 0
                    if (totalBytes > 0) {
                        progress = ((req.downloadedBytes * 100) / totalBytes).toInt()
                    }
                    onProgress(progress)
                } while (true)

                val path = getPath(req.dirPath, req.fileName)
                renameFileName(tempPath, path)
                onCompleted()
                req.status = Status.COMPLETED
                return@withContext
            } catch (e: CancellationException) {
                deleteTempFile()
                req.status = Status.FAILED
                onError(e.toString())
                return@withContext
            } catch (e: Exception) {
                if (!isResumeSupported) {
                    deleteTempFile()
                }
                req.status = Status.FAILED
                onError(e.toString())
                return@withContext
            } finally {
                closeAllSafely(outputStream)
            }
        }
    }

    private fun deleteTempFile(): Boolean {
        val file = File(tempPath)
        if (file.exists()) {
            return file.delete()
        }
        return false
    }

    private suspend fun closeAllSafely(outputStream: FileDownloadOutputStream) {

        try {
            httpClient.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            inputStream!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        try {
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}
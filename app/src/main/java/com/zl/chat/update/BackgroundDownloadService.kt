package com.zl.chat.update

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.tencent.mars.xlog.Log
import com.zl.chat.R
import com.zl.core.api.ServiceGenerator
import com.zl.core.utils.FileUtils


class BackgroundDownloadService : IntentService("BackgroundDownloadService") {

    private val channelId = "BackgroundDownloadService"
    private val channelName = "下载"

    companion object {
        val DOWNLOAD_URL = "DownloadServiceUrl"
        val DOWNLOAD_FILE_NAME = "DownloadServiceFileName"

        fun start(context: Context, url: String, fileName: String){
            val intent = Intent(context, BackgroundDownloadService::class.java)
            intent.putExtra(DOWNLOAD_URL, url)
            intent.putExtra(DOWNLOAD_FILE_NAME, fileName)
            context.startService(intent)
        }
    }

    private val TAG = BackgroundDownloadService::class.java.simpleName
    private val FILE_DIR = "download"

    private val NOTIFY_ID = 0x11

    private var mRate = 0

    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        mBuilder = NotificationCompat.Builder(this, channelId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val url = intent.getStringExtra(DOWNLOAD_URL)
            val fileName = intent.getStringExtra(DOWNLOAD_FILE_NAME)
            if (url == null || fileName == null) {
                return
            }
            download(url, fileName)
        }
    }

    @SuppressLint("MissingPermission", "CheckResult")
    private fun download(url: String, fileName: String) {
        setUpNotification()

        val api = ServiceGenerator.createDownloadService(DownloadApi::class.java) { bytesRead, contentLength, done ->
            Log.i(TAG, "download: $bytesRead of $contentLength")

            var rate = if (contentLength <= 0) {
                0.0
            } else {
                bytesRead.toDouble() / contentLength * 100
            }
            if (done) {
                rate = 100.0
            }

            if (mRate != rate.toInt()) {
                mRate = rate.toInt()
                updateRate(mRate, fileName)
            }

            if (done) {
                updateRate(100, fileName)
            }
        }

        api.downloadFileWithFixedUrl(url)
                .subscribe({
                    FileUtils.writeResponseBodyToDisk(FILE_DIR, fileName, it)
                }, {
                    Log.i(TAG, "download: ${it.message}")
                })
    }

    @SuppressLint("MissingPermission")
    private fun updateRate(rate: Int, fileName: String) {
        when {
            rate == 0 -> mBuilder.setProgress(100, rate, true)
            rate < 100 -> {
                // 更新进度
                Log.i(TAG, "rate:$rate")
                mBuilder.setProgress(100, rate, false)

            }
            rate == 100 -> {
                val intent = Intent(Intent.ACTION_VIEW)


                val type = FileUtils.getMIMEType(fileName)
                val file = FileUtils.getFile(FILE_DIR, fileName) ?: return

                val uri = FileUtils.getUriForFile(this, file)
                intent.setDataAndType(uri, type)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                mBuilder.setContentIntent(pendingIntent)

                FileUtils.openFile(this, FILE_DIR, fileName)
            }
        }
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build())
    }

    /**
     * 创建通知
     */
    private fun setUpNotification() {

        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("更新")
//                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setProgress(100, 0, false)
//        val intent = Intent(this@BackgroundDownloadService, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this@BackgroundDownloadService, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
//        mBuilder.setContentIntent(pendingIntent)

        startForeground(NOTIFY_ID, mBuilder.build())
    }
}
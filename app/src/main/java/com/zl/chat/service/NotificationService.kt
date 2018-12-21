package com.zl.chat.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.tencent.mars.wrapper.remote.PushMessageHandler
import com.zl.chat.R
import android.app.NotificationChannel



/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/21 14:54.<br/>
 */
class NotificationService : Service() {

    private val channelId = "NotificationService"
    private val channelName = "NotificationService"

    private lateinit var notificationManager: NotificationManager
    private lateinit var mBuilder: NotificationCompat.Builder

    private val handler: PushMessageHandler = PushMessageHandler {

        mBuilder.setContentTitle("fixme")
            .setContentText(it.messageString)
            .setTicker("新消息")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setWhen(System.currentTimeMillis())

        notificationManager.notify(100, mBuilder.build())
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        mBuilder = NotificationCompat.Builder(this, channelId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }

        MarsServiceProxy.inst.addPushMessageHandler(handler)
    }

    override fun onDestroy() {
        super.onDestroy()
        MarsServiceProxy.inst.removePushMessageHandler(handler)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
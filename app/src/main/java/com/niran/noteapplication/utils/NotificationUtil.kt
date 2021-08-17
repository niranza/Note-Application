package com.niran.noteapplication.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.niran.noteapplication.MainActivity
import com.niran.noteapplication.R

object NotificationUtil {
    private const val NOTIFICATION_ID = 0

    fun sendNotification(text: String, context: Context) {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setContentTitle(context.getString(R.string.note_reminder))
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_star)

        val manager = getManager(context)

        manager.cancelAll()

        manager.notify(NOTIFICATION_ID, notificationBuilder.build())

    }


    fun createNotificationChannel(context: Context, channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setShowBadge(false)
            }

            getManager(context).createNotificationChannel(channel)
        }
    }

    private fun getManager(context: Context): NotificationManager =
        ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
}
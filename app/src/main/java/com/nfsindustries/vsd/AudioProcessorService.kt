package com.nfsindustries.vsd

import android.app.*
import android.content.Intent
import android.media.AudioFormat
import android.os.IBinder
import java.util.concurrent.Executors

const val SAMPLE_RATE = 8000
const val CHANNELS = AudioFormat.CHANNEL_IN_MONO
const val AUDIO_ENCODING = AudioFormat.ENCODING_PCM_FLOAT

class AudioProcessorService : Service() {

    private var mBinder: IBinder? = null
    private val micCaptureRunnable = MicCaptureRunnable()
    private var executorService = Executors.newSingleThreadExecutor()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        prepareToForeground()
        micCaptureRunnable.setContext(this.applicationContext)
        executorService.submit(micCaptureRunnable)
    }

    private fun prepareToForeground() {
        val pendingIntent: PendingIntent =
            Intent(this, AudioProcessorService::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val name = getString(R.string.notification_name)
        val descriptionText = getString(R.string.notification_message)
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(NOTIF_CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        val notification: Notification = Notification.Builder(this, NOTIF_CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setContentIntent(pendingIntent)
            .setTicker(getText(R.string.notification_message))
            .setSmallIcon(R.drawable.ic_stat_sentiment_neutral)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        executorService.shutdownNow()
        return super.onUnbind(intent)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        executorService.shutdownNow()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdownNow()
    }

}
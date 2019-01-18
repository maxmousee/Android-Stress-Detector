package com.nfsindustries.vsd

import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import java.util.concurrent.Executors

const val RECORDER_SAMPLE_RATE = 8000
const val RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO
const val RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_FLOAT

class AudioProcessorService() : Service() {

    private var mBinder: IBinder? = null
    private val micCaptureRunnable = MicCaptureRunnable()
    private var executorService = Executors.newSingleThreadExecutor()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        executorService.submit(micCaptureRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdownNow()
    }

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            if (Looper.getMainLooper().equals(Looper.myLooper())) {
//                Log.d(LOG_TAG, "HI")
            }
        }
    }

}
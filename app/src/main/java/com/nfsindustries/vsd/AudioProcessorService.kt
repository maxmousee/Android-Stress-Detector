package com.nfsindustries.vsd

import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioRecord.READ_BLOCKING
import android.media.MediaRecorder
import android.os.Handler
import android.os.IBinder
import android.util.Log
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

    class MicCaptureRunnable() : Runnable {

        private val frequencyStringConverter = FrequencyStringConverter()
        private var audioDoubleArray = DoubleArray(RECORDER_SAMPLE_RATE)
        private var audioData = FloatArray(RECORDER_SAMPLE_RATE)
        private val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLE_RATE)
        private val vsd = VSDJNI()
        private var stressFrequency = 0.0

        private fun readAudioBuffer() {
            // gets the voice output from microphone to byte format
            recorder.read(audioData, 0, RECORDER_SAMPLE_RATE, READ_BLOCKING)
            var index = 0
            for (value in audioData) {
                audioDoubleArray.set(index, value.toDouble())
                index++
            }
            stressFrequency = vsd.processAudio(audioDoubleArray)
            val formattedString = frequencyStringConverter.convertStressFrequencyFormattedString(stressFrequency)
            Log.d(LOG_TAG, stressFrequency.toString() + " Hz")
        }

        override fun run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
            while (true) {
                readAudioBuffer()
            }
        }
    }

}
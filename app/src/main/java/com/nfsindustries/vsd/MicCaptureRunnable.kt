package com.nfsindustries.vsd

import android.content.Context
import android.media.AudioRecord
import android.media.AudioRecord.*
import android.media.MediaRecorder.AudioSource.*
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.os.Process.*

class MicCaptureRunnable : Runnable {

    private val frequencyStringConverter = FrequencyConverter()
    private val minSizeInBytes = getMinBufferSize(SAMPLE_RATE, CHANNELS, AUDIO_ENCODING)
    private val recorder = AudioRecord(MIC, SAMPLE_RATE, CHANNELS, AUDIO_ENCODING, 8192)
    private var audioDoubleArray = DoubleArray(SAMPLE_RATE)
    private var audioData = FloatArray(SAMPLE_RATE)
    private val vsd = VSDJNI()
    private var stressFrequency = 0.0
    private var mHandler: Handler? = null
    private var audioStr = String()
    private var context: Context? = null

    private fun readAudioBuffer() {
        // gets the voice output from microphone to byte format
        val status = recorder.read(audioData, 0, SAMPLE_RATE, READ_BLOCKING)
        var index = 0
        for (value in audioData) {
            audioDoubleArray[index] = value.toDouble()
            index++
            audioStr += " %.4f".format(value)
        }
        stressFrequency = vsd.processAudio(audioDoubleArray)
        Log.d(LOG_TAG, "Read Status: $status")
        Log.d(LOG_TAG, "Data $audioStr")
        Log.d(LOG_TAG, "$stressFrequency Hz")
        audioStr = String()
    }

    private fun updateUI(formattedString: String, color:String) {
        mHandler = Handler(Looper.getMainLooper())
        (AppConstants.activity as RealTime).setTextViewTextAndColor(formattedString, color)
    }

    fun setContext(context: Context) {
        this.context = context
    }

    private fun getValidSampleRates(): Boolean {
        for (rate in intArrayOf(SAMPLE_RATE)) {  // add the rates you wish to check against
            val bufferSize = AudioRecord.getMinBufferSize(rate, CHANNELS, AUDIO_ENCODING)
            if (bufferSize > 0) {
                // buffer size is valid, Sample rate supported
                Log.d(LOG_TAG, "supported rate: $rate")
                return true
            }
        }
        return false
    }

    override fun run() {
        Log.d(LOG_TAG, "minSizeInBytes: $minSizeInBytes")
        getValidSampleRates()
        setThreadPriority(THREAD_PRIORITY_AUDIO)
        while (true) {
            readAudioBuffer()
            val formattedString = frequencyStringConverter.convertStressFrequencyFormattedString(stressFrequency)
            val color = frequencyStringConverter.convertBackgroundColor(stressFrequency)
//            updateUI(formattedString, color)
        }
    }
}

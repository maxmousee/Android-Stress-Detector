package com.nfsindustries.vsd

import android.media.AudioRecord
import android.media.AudioRecord.READ_BLOCKING
import android.media.MediaRecorder.AudioSource.MIC
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.util.Log

class MicCaptureRunnable : Runnable {

    private val frequencyStringConverter = FrequencyConverter()
    private var audioDoubleArray = DoubleArray(RECORDER_SAMPLE_RATE)
    private var audioData = FloatArray(RECORDER_SAMPLE_RATE)
    private val recorder = AudioRecord(MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLE_RATE)
    private val vsd = VSDJNI()
    private var stressFrequency = 0.0

    private fun readAudioBuffer() {
        // gets the voice output from microphone to byte format
        recorder.read(audioData, 0, RECORDER_SAMPLE_RATE, READ_BLOCKING)
        var index = 0
        for (value in audioData) {
            audioDoubleArray[index] = value.toDouble()
            index++
        }
        stressFrequency = vsd.processAudio(audioDoubleArray)
        val formattedString = frequencyStringConverter.convertStressFrequencyFormattedString(stressFrequency)
        val color = frequencyStringConverter.convertBackgroundColor(stressFrequency)
        if(stressFrequency > 0) Log.d(LOG_TAG, "$stressFrequency Hz")
    }

    override fun run() {
        android.os.Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND)
        while (true) {
            readAudioBuffer()
        }
    }
}

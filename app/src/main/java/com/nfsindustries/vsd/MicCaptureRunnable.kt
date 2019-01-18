package com.nfsindustries.vsd

import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log

class MicCaptureRunnable() : Runnable {

    private val frequencyStringConverter = FrequencyStringConverter()
    private var audioDoubleArray = DoubleArray(RECORDER_SAMPLE_RATE)
    private var audioData = FloatArray(RECORDER_SAMPLE_RATE)
    private val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLE_RATE)
    private val vsd = VSDJNI()
    private var stressFrequency = 0.0

    private fun readAudioBuffer() {
        // gets the voice output from microphone to byte format
        recorder.read(audioData, 0, RECORDER_SAMPLE_RATE, AudioRecord.READ_BLOCKING)
        var index = 0
        for (value in audioData) {
            audioDoubleArray[index] = value.toDouble()
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
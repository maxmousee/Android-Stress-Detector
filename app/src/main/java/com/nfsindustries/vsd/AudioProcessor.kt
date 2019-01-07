package com.nfsindustries.vsd

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioRecord.READ_BLOCKING
import android.media.MediaRecorder
import android.os.Handler
import android.util.Log
import android.os.Looper
import android.os.Message
import java.util.*

const val RECORDER_SAMPLE_RATE = 8000
const val RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO
const val RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_FLOAT

class AudioProcessor() : Runnable {
    private var isRecording = false
    private val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLE_RATE)
    private val frequencyStringConverter = FrequencyStringConverter()
    private val vsd = VSDJNI()
    private var stressFrequency = 0.0

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            if (Looper.getMainLooper().equals(Looper.myLooper())) {
//                Log.d(LOG_TAG, "HI")
            }
        }
    }

    override fun run() {
        while (isRecording) {
            readAudioBuffer()
//            mHandler.sendEmptyMessage(0)
        }
    }

    fun readAudioBuffer() {
        var audioData = FloatArray(RECORDER_SAMPLE_RATE)
        // gets the voice output from microphone to byte format
        recorder.read(audioData, 0, RECORDER_SAMPLE_RATE, READ_BLOCKING)
        var audioDoubleArray = DoubleArray(RECORDER_SAMPLE_RATE)
        var index = 0
        for (value in audioData) {
            audioDoubleArray.set(index, value.toDouble())
            index++
        }
        stressFrequency = vsd.processAudio(audioDoubleArray)
        Log.d(LOG_TAG, stressFrequency.toString() + " Hz")
    }

    fun startCapturingBuffer() {
        recorder.startRecording()
        isRecording = true
    }

    fun stopCapturingBuffer() {
        recorder.stop()
        isRecording = false
    }

    fun isRecording(): Boolean {
        return isRecording
    }
}
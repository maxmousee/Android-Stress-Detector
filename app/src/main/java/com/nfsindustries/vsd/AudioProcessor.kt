package com.nfsindustries.vsd

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioRecord.READ_BLOCKING
import android.media.MediaRecorder
import android.os.AsyncTask
import android.util.Log
import java.nio.ByteBuffer

const val LOG_TAG = "VSD"
const val RECORDER_SAMPLE_RATE = 8000
const val RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO
const val RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_FLOAT

class AudioProcessor() : AsyncTask<Void, Int, Double>() {
    private var isRecording = false
    private val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLE_RATE)
    private val frequencyStringConverter = FrequencyStringConverter()
    private val vsd = VSDNative()
    private var stressFrequency = 0.0

    override fun doInBackground(vararg params: Void): Double {
        readAudioBuffer()
//        stressFrequency = vsd.processAudio(params.get(0))
        return stressFrequency
    }

    override fun onPostExecute(result: Double?) {
        super.onPostExecute(result)
    }

    fun readAudioBuffer() {
        var audioData = FloatArray(RECORDER_SAMPLE_RATE)
        // gets the voice output from microphone to byte format
        recorder.read(audioData, 0, RECORDER_SAMPLE_RATE, READ_BLOCKING)
        Log.d(LOG_TAG, audioData.get(0).toString())
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

    fun toDouble(bytes: ByteArray): Double {
        return ByteBuffer.wrap(bytes).double
    }
}
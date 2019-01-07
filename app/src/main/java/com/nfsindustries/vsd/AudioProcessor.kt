package com.nfsindustries.vsd

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder.AudioSource.MIC
import android.util.Log
import java.nio.ByteBuffer

const val LOG_TAG = "VSD"
const val RECORDER_SAMPLE_RATE = 8000
const val RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO
const val RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT

class AudioProcessor() {

    private var isRecording = false
    private val recorder = AudioRecord(MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLE_RATE)

    fun readAudioBuffer() {
        val audioData = ByteArray(RECORDER_SAMPLE_RATE)
        while (isRecording) {
            // gets the voice output from microphone to byte format
            recorder.read(audioData, 0, RECORDER_SAMPLE_RATE)
            Log.d(LOG_TAG, audioData.toString())
        }
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
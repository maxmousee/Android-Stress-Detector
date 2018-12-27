package com.nfsindustries.vsd

import android.media.AudioFormat
import android.media.MediaRecorder
import android.media.AudioRecord
import android.util.Log

class AudioProcessor() {

    private var isRecording = false
    private val LOG_TAG = "VSD"
    private val RECORDER_SAMPLERATE = 8000
    private val RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO
    private val RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT

    private val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, RECORDER_SAMPLERATE)

    fun readAudioBuffer() {
        var audioData = ByteArray(RECORDER_SAMPLERATE)
        while (isRecording) {
            // gets the voice output from microphone to byte format
            recorder.read(audioData, 0, RECORDER_SAMPLERATE)
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

    /**
     * A native method that is implemented by the 'vsd' native library,
     * which is packaged with this application.
     */
    external fun processAudio(array: Array<Double>): Double

    companion object {

        // Used to load the 'vsd' native cpp library on application startup.
        init {
            System.loadLibrary("vsd")
        }
    }
}
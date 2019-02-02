package com.nfsindustries.vsd

import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_real_time.*

class RealTime : AppCompatActivity() {

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(RECORD_AUDIO)

    private val frequencyConverter = FrequencyConverter()
    private val stressFrequency = 10.0

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted =
                if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) { grantResults[0] == PERMISSION_GRANTED }
                else { false }
        checkPermissionDenied()
    }

    private fun checkPermissionDenied() {
        if (!permissionToRecordAccepted) {
            displayPermissionDeniedToast()
            finish()
        }
    }

    private fun displayPermissionDeniedToast() {
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, "Please, allow VSD to record audio. This is required ;)", duration)
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)
        setTextViewBackgroundColor(stressFrequency)
        freq_tv.text = frequencyConverter.convertStressFrequencyFormattedString(stressFrequency)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        startListening()
    }

    private fun setTextViewBackgroundColor(frequency: Double) {
        val color = Color.parseColor(frequencyConverter.convertBackgroundColor(frequency))
        freq_tv.setBackgroundColor(color)
    }

    private fun startListening() {
        if(permissionToRecordAccepted) {
            Intent(this, AudioProcessorService::class.java).also { intent ->
                startService(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startListening()
    }

    override fun onPause() {
        super.onPause()
        Intent(this, AudioProcessorService::class.java).also { intent ->
            stopService(intent)
        }
    }
}

package com.nfsindustries.vsd

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_real_time.*

class RealTime : AppCompatActivity() {

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(RECORD_AUDIO)

    private val frequencyConverter = FrequencyConverter()
    private val stressFrequency = 0.0

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted =
                if (requestCode == REQUEST_PERMISSION) { grantResults[0] == PERMISSION_GRANTED }
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
        freq_tv.text = getString(R.string.loading_text)
        freq_tv.setBackgroundColor(Color.GRAY)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION)
    }

    private fun setTextViewBackgroundColor(frequency: Double) {
        val color = Color.parseColor(frequencyConverter.convertBackgroundColor(frequency))
        freq_tv.setBackgroundColor(color)
    }

    fun setTextViewTextAndColor(formattedString: String, colorString: String) {
        freq_tv.setBackgroundColor(Color.parseColor(colorString))
        freq_tv.text = formattedString
    }

    private fun startListening() {
        if(permissionToRecordAccepted) {
            Intent(this, AudioProcessorService::class.java).also { intent ->
                startForegroundService(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        AppConstants.activity = this
        startListening()
    }

    override fun onPause() {
        super.onPause()
        AppConstants.activity = null
        Intent(this, AudioProcessorService::class.java).also { intent ->
            stopService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppConstants.activity = null
    }
}

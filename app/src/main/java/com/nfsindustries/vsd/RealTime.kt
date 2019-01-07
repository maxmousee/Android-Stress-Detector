package com.nfsindustries.vsd

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_real_time.*

const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class RealTime : AppCompatActivity() {

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private val frequencyStringConverter = FrequencyStringConverter()
    private val stressFrequency = 10.0

    var audioProcessor = AudioProcessor()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted =
                if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) { grantResults[0] == PackageManager.PERMISSION_GRANTED }
                else { false }
        checkPermissionDenied()
    }

    fun checkPermissionDenied() {
        if (!permissionToRecordAccepted) {
            displayPermissionDeniedToast()
            finish()
        }
    }

    fun displayPermissionDeniedToast() {
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, "Please, allow VSD to record audio. This is required ;)", duration)
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        freq_tv.text = frequencyStringConverter.convertStressFrequencyFormattedString(stressFrequency)
        setTextViewBackgroundColor(stressFrequency)
    }

    fun setTextViewBackgroundColor(frequency: Double) {
        if(frequency < 7.0 || frequency >= 13.0) {
            freq_tv.setBackgroundColor(resources.getColor(R.color.colorStress))
        } else if (frequency >= 7.0 && frequency < 8.0) {
            freq_tv.setBackgroundColor(resources.getColor(R.color.colorMarginalStress))
        } else if (frequency > 12.0 && frequency < 13.0) {
            freq_tv.setBackgroundColor(resources.getColor(R.color.colorMarginalStress))
        } else {
            freq_tv.setBackgroundColor(resources.getColor(R.color.colorNoStress))
        }
    }

    override fun onResume() {
        super.onResume()
        audioProcessor.startCapturingBuffer()
    }

    override fun onPause() {
        super.onPause()
        audioProcessor.stopCapturingBuffer()
    }
}

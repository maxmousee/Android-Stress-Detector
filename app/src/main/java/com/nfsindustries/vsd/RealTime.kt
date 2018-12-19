package com.nfsindustries.vsd

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_real_time.*

class RealTime : AppCompatActivity() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted =
                if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                } else {
                    false
                }
        if (!permissionToRecordAccepted) finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        freq_tv.text = "Done"
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

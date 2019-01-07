package com.nfsindustries.vsd

import android.util.Log

class VSDJNI {

    // --- Native VSD method
    external fun processAudio(inputArray: DoubleArray): Double

    companion object {

        init {
            try {
                System.loadLibrary("VSDJNI")
            } catch (e: UnsatisfiedLinkError) {
                Log.e(LOG_TAG, e.message)
            }

        }
    }
}

package com.nfsindustries.vsd

class VSDNative {
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
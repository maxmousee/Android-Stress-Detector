package com.nfsindustries.vsd

import android.os.AsyncTask
import android.widget.TextView

class AudioProcessorRunner(textView: TextView) : AsyncTask<Array<Double>, Int, Double>() {

    private val frequencyStringConverter = FrequencyStringConverter()
    private val vsd = VSDNative()
    private var stressFrequency = 0.0
    private var textView = textView

    override fun doInBackground(vararg params: Array<Double>): Double {
        stressFrequency = vsd.processAudio(params.get(0))
        return stressFrequency
    }

    override fun onPostExecute(result: Double?) {
        super.onPostExecute(result)
        textView.text = "Done"
    }

}
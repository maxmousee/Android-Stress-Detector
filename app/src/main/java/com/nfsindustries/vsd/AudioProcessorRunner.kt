package com.nfsindustries.vsd

import android.os.AsyncTask

class AudioProcessorRunner : AsyncTask<Array<Double>, Int, Long>() {

    var audioProcessor = AudioProcessor()
    private var stressCoeficient = 10.0

    override fun doInBackground(vararg params: Array<Double>?): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostExecute(result: Long?) {
        super.onPostExecute(result)
        //Update UI
    }

}
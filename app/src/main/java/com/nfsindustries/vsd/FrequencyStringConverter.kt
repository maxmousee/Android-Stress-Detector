package com.nfsindustries.vsd

class FrequencyStringConverter {

    fun convertStressCoeficientEmoji(frequency: Double): String {
        if(frequency < 7.0 || frequency >= 13.0) {
            return "\uD83D\uDE21"
        } else if (frequency >= 7.0 && frequency < 8.0) {
            return "\uD83D\uDE10"
        } else if (frequency > 12.0 && frequency < 13.0) {
            return "\uD83D\uDE10"
        } else {
            return "\uD83D\uDE00"
        }
    }

    fun convertStressCoeficientString(frequency: Double): String {
        if(frequency < 7.0 || frequency >= 13.0) {
            return "Stress"
        } else if (frequency >= 7.0 && frequency < 8.0) {
            return "Marginal Stress"
        } else if (frequency > 12.0 && frequency < 13.0) {
            return "Marginal Stress"
        } else {
            return "No Stress"
        }
    }
}
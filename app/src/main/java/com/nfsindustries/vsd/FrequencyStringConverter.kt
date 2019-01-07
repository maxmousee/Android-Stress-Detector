package com.nfsindustries.vsd

class FrequencyStringConverter {

    fun convertStressFrequencyEmoji(frequency: Double): String {
        if(frequency < MARGINAL_STRESS_LOWER_LIMIT || frequency >= STRESS_UPPER_LIMIT) {
            return "\uD83D\uDE21"
        } else if (frequency >= MARGINAL_STRESS_LOWER_LIMIT && frequency < STRESS_LOWER_LIMIT) {
            return "\uD83D\uDE10"
        } else if (frequency > MARGINAL_STRESS_UPPER_LIMIT && frequency < STRESS_UPPER_LIMIT) {
            return "\uD83D\uDE10"
        } else {
            return "\uD83D\uDE00"
        }
    }

    fun convertStressFrequencyString(frequency: Double): String {
        if(frequency < MARGINAL_STRESS_LOWER_LIMIT || frequency >= STRESS_UPPER_LIMIT) {
            return "Stress"
        } else if (frequency >= MARGINAL_STRESS_LOWER_LIMIT && frequency < STRESS_LOWER_LIMIT) {
            return "Marginal Stress"
        } else if (frequency > MARGINAL_STRESS_UPPER_LIMIT && frequency < STRESS_UPPER_LIMIT) {
            return "Marginal Stress"
        } else {
            return "No Stress"
        }
    }

    fun convertStressFrequencyFormattedString(frequency: Double): String {
        return frequency.toString() + " Hz\n" + convertStressFrequencyString(frequency) + "\n" +
                convertStressFrequencyEmoji(frequency)
    }
}
package com.nfsindustries.vsd

class FrequencyStringConverter {

    private fun getStressStatus(frequency: Double): StressStatus{
        if(frequency < MARGINAL_STRESS_LOWER_LIMIT || frequency >= STRESS_UPPER_LIMIT) {
            return StressStatus.STRESS
        } else if (frequency >= MARGINAL_STRESS_LOWER_LIMIT && frequency < STRESS_LOWER_LIMIT) {
            return StressStatus.MARGINAL_STRESS
        } else if (frequency > MARGINAL_STRESS_UPPER_LIMIT && frequency < STRESS_UPPER_LIMIT) {
            return StressStatus.MARGINAL_STRESS
        } else {
            return StressStatus.NO_STRESS
        }
    }

    fun convertStressFrequencyEmoji(frequency: Double): String {
        val stressStatus = getStressStatus(frequency)
        when (stressStatus) {
            StressStatus.STRESS -> return "\uD83D\uDE21"
            StressStatus.MARGINAL_STRESS -> return "\uD83D\uDE10"
            else -> return "\uD83D\uDE00"
        }
    }

    fun convertStressFrequencyString(frequency: Double): String {
        val stressStatus = getStressStatus(frequency)
        when (stressStatus) {
            StressStatus.STRESS -> return STRESS_STRING
            StressStatus.MARGINAL_STRESS -> return MARGINAL_STRESS_STRING
            else -> return NO_STRESS_STRING
        }
    }

    fun convertStressFrequencyFormattedString(frequency: Double): String {
        return frequency.toString() + " Hz\n" + convertStressFrequencyString(frequency) + "\n" +
                convertStressFrequencyEmoji(frequency)
    }
}

package com.nfsindustries.vsd

import org.junit.Test

import org.junit.Assert.*

class FrequencyStringConverterTest {
    val converter = FrequencyStringConverter()

    @Test
    fun convertStressCoeficientStringNoStressLow() {
        val text = converter.convertStressFrequencyString(8.0);
        assertEquals("No Stress", text)
    }

    @Test
    fun convertStressCoeficientStringNoStressHigh() {
        val text = converter.convertStressFrequencyString(12.0);
        assertEquals("No Stress", text)
    }

    @Test
    fun convertStressCoeficientStringStressLow() {
        val text = converter.convertStressFrequencyString(6.99);
        assertEquals("Stress", text)
    }

    @Test
    fun convertStressCoeficientStringStressHigh() {
        val text = converter.convertStressFrequencyString(13.0);
        assertEquals("Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressLow() {
        val text = converter.convertStressFrequencyString(7.0);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressLow2() {
        val text = converter.convertStressFrequencyString(7.99);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressHigh() {
        val text = converter.convertStressFrequencyString(12.01);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressHigh2() {
        val text = converter.convertStressFrequencyString(12.99);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientEmojiNoStressLow() {
        val text = converter.convertStressFrequencyEmoji(8.0);
        assertEquals("\uD83D\uDE00", text)
    }

    @Test
    fun convertStressCoeficientEmojiNoStressHigh() {
        val text = converter.convertStressFrequencyEmoji(12.0);
        assertEquals("\uD83D\uDE00", text)
    }

    @Test
    fun convertStressCoeficientEmojiStressLow() {
        val text = converter.convertStressFrequencyEmoji(6.99);
        assertEquals("\uD83D\uDE21", text)
    }

    @Test
    fun convertStressCoeficientEmojiStressHigh() {
        val text = converter.convertStressFrequencyEmoji(13.0);
        assertEquals("\uD83D\uDE21", text)
    }

    @Test
    fun convertStressCoeficientEmojiMarginalStressLow() {
        val text = converter.convertStressFrequencyEmoji(7.0);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressCoeficientEmojiMarginalStressLow2() {
        val text = converter.convertStressFrequencyEmoji(7.99);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressCoeficientEmojiMarginalStressHigh() {
        val text = converter.convertStressFrequencyEmoji(12.01);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressCoeficientEmojiMarginalStressHigh2() {
        val text = converter.convertStressFrequencyEmoji(12.99);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressCoeficientFormattedStringStressHigh() {
        val text = converter.convertStressFrequencyFormattedString(13.0);
        assertEquals("13.0 Hz\nStress\n\uD83D\uDE21", text)
    }
}
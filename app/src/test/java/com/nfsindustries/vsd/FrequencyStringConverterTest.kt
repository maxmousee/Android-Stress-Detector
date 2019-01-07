package com.nfsindustries.vsd

import org.junit.Test

import org.junit.Assert.*

class FrequencyStringConverterTest {
    val converter = FrequencyStringConverter()

    @Test
    fun convertStressFrequencyStringNoStressLow() {
        val text = converter.convertStressFrequencyString(8.0);
        assertEquals("No Stress", text)
    }

    @Test
    fun convertStressFrequencyStringNoStressHigh() {
        val text = converter.convertStressFrequencyString(12.0);
        assertEquals("No Stress", text)
    }

    @Test
    fun convertStressFrequencyStringStressLow() {
        val text = converter.convertStressFrequencyString(6.99);
        assertEquals("Stress", text)
    }

    @Test
    fun convertStressFrequencyStringStressHigh() {
        val text = converter.convertStressFrequencyString(13.0);
        assertEquals("Stress", text)
    }

    @Test
    fun convertStressFrequencyStringMarginalStressLow() {
        val text = converter.convertStressFrequencyString(7.0);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressFrequencyStringMarginalStressLow2() {
        val text = converter.convertStressFrequencyString(7.99);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressFrequencyStringMarginalStressHigh() {
        val text = converter.convertStressFrequencyString(12.01);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressFrequencyStringMarginalStressHigh2() {
        val text = converter.convertStressFrequencyString(12.99);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressFrequencyEmojiNoStressLow() {
        val text = converter.convertStressFrequencyEmoji(8.0);
        assertEquals("\uD83D\uDE00", text)
    }

    @Test
    fun convertStressFrequencyEmojiNoStressHigh() {
        val text = converter.convertStressFrequencyEmoji(12.0);
        assertEquals("\uD83D\uDE00", text)
    }

    @Test
    fun convertStressFrequencyEmojiStressLow() {
        val text = converter.convertStressFrequencyEmoji(6.99);
        assertEquals("\uD83D\uDE21", text)
    }

    @Test
    fun convertStressFrequencyEmojiStressHigh() {
        val text = converter.convertStressFrequencyEmoji(13.0);
        assertEquals("\uD83D\uDE21", text)
    }

    @Test
    fun convertStressFrequencyEmojiMarginalStressLow() {
        val text = converter.convertStressFrequencyEmoji(7.0);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressFrequencyEmojiMarginalStressLow2() {
        val text = converter.convertStressFrequencyEmoji(7.99);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressFrequencyEmojiMarginalStressHigh() {
        val text = converter.convertStressFrequencyEmoji(12.01);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressFrequencyEmojiMarginalStressHigh2() {
        val text = converter.convertStressFrequencyEmoji(12.99);
        assertEquals("\uD83D\uDE10", text)
    }

    @Test
    fun convertStressFrequencyFormattedStringStressHigh() {
        val text = converter.convertStressFrequencyFormattedString(13.0);
        assertEquals("13.0 Hz\nStress\n\uD83D\uDE21", text)
    }
}
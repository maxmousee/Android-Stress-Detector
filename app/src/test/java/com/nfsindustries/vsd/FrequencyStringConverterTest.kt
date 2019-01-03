package com.nfsindustries.vsd

import org.junit.Test

import org.junit.Assert.*

class FrequencyStringConverterTest {
    val converter = FrequencyStringConverter()

    @Test
    fun convertStressCoeficientStringNoStressLow() {
        val text = converter.convertStressCoeficientString(8.0);
        assertEquals("No Stress", text)
    }

    @Test
    fun convertStressCoeficientStringNoStressHigh() {
        val text = converter.convertStressCoeficientString(12.0);
        assertEquals("No Stress", text)
    }

    @Test
    fun convertStressCoeficientStringStressLow() {
        val text = converter.convertStressCoeficientString(6.99);
        assertEquals("Stress", text)
    }

    @Test
    fun convertStressCoeficientStringStressHigh() {
        val text = converter.convertStressCoeficientString(13.0);
        assertEquals("Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressLow() {
        val text = converter.convertStressCoeficientString(7.0);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressLow2() {
        val text = converter.convertStressCoeficientString(7.99);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressHigh() {
        val text = converter.convertStressCoeficientString(12.01);
        assertEquals("Marginal Stress", text)
    }

    @Test
    fun convertStressCoeficientStringMarginalStressHigh2() {
        val text = converter.convertStressCoeficientString(12.99);
        assertEquals("Marginal Stress", text)
    }
}
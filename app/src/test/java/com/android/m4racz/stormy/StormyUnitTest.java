package com.android.m4racz.stormy;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StormyUnitTest {
    private static final String TAG = StormyUnitTest.class.getSimpleName();
    private OpenWeatherCurrentWeather openWeatherCurrentWeather;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testJSONJacksonParser(){
        openWeatherCurrentWeather = new OpenWeatherCurrentWeather();
    }



}
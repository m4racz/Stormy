package com.android.m4racz.stormy;

import com.android.m4racz.stormy.CurrentWeather.CurrentWeather;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StormyUnitTest {
    private static final String TAG = StormyUnitTest.class.getSimpleName();
    private CurrentWeather currentWeather;
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testJSONJacksonParser(){
        currentWeather = new CurrentWeather();
    }



}
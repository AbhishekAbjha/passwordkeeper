package com.coronate.passwordkeeper.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class WhenDecryptingInput {

    @Before
    public void setup(){
    }

    @Test
    public void thenGetEncryptedInput(){
        assertEquals("Abhishek", Crypt.decrypt("abhishekabhi2828", "Abhishek"));
    }

    @After
    public void tearDown(){
    }
}

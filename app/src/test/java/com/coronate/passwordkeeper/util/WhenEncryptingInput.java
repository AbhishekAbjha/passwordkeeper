package com.coronate.passwordkeeper.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhenEncryptingInput {

    @Before
    public void setup(){
    }

    @Test
    public void thenGetEncryptedInput(){
        assertEquals("Abhishek", Crypt.encrypt("abhishekabhi2828", "Abhishek"));
    }

    @After
    public void tearDown(){

    }
}

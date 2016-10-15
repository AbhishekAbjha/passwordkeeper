package com.coronate.passwordkeeper.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

public class WhenTestingTimeStampUtility {

    @Before
    public void setup(){

    }

    @Test
    public void thenGetCurrentTimeStamp(){
        assertNotEquals("11:06:2016 20:00:07", TimeStamp.getCurrentTimeStamp());
    }

    @After
    public void tearDown(){

    }
}

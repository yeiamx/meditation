package com.flctxx.meditation;


import org.junit.runner.RunWith;


import org.junit.runners.Suite;

import com.flctxx.meditation.api.RunAllAPITests;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        RunAllAPITests.class,
        RunAllNonAPITest.class
})
public class RunAllTests {
}

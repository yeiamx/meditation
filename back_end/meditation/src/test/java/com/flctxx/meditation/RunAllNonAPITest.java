package com.flctxx.meditation;

import org.junit.runner.RunWith;


import org.junit.runners.Suite;

import com.flctxx.meditation.service.RunAllServiceTests;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        RunAllServiceTests.class,
})
public class RunAllNonAPITest {
}

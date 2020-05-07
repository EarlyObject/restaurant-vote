package com.earlyobject.ws;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"com.earlyobject.ws.shared"})
public class TestConfig {
}

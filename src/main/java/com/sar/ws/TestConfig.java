package com.sar.ws;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"com.sar.ws.shared"})
public class TestConfig {
}

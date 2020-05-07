package com.earlyobject.ws.shared.dto;

import com.earlyobject.ws.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCurrentDate() {
        LocalDate currentDate = Utils.getCurrentDate();
        assertNotNull(currentDate);
        assertEquals(currentDate, LocalDate.now());
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(20);
        String userId2 = utils.generateUserId(20);
        assertNotNull(userId);
        assertEquals(20, userId.length());
        assertFalse(userId.equalsIgnoreCase(userId2));
    }
}
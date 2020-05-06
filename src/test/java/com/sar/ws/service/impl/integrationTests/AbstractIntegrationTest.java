package com.sar.ws.service.impl.integrationTests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql("classpath:db/populateDB.sql")
abstract public class AbstractIntegrationTest {
}

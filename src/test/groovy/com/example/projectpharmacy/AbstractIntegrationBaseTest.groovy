package com.example.projectpharmacy

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest
abstract class AbstractIntegrationBaseTest extends Specification{
    static final GenericContainer MY_REDIS_CONTAINER


    static{
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
        .withExposedPorts(6379)

        MY_REDIS_CONTAINER.start()

        System.setProperty("spring.data.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.data.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())
    }

}

package com.technicalaudition.loadbalancer;

import com.technicalaudition.loadbalancer.servers.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerUnitTest {

    private static final String SERVER_1_NAME = "Server 1";

    @Autowired
    private static Server server;

    @Test
    void testRoundRobin_server1() {
        assertEquals(server.getHost(), SERVER_1_NAME);
    }
}

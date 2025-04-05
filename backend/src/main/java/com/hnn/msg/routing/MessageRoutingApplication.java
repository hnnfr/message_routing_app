package com.hnn.msg.routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication(exclude = { JmsAutoConfiguration.class })
public class MessageRoutingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageRoutingApplication.class, args);
    }

}

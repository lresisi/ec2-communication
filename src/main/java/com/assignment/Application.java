package com.assignment;

import com.assignment.inet.InetAddressUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    // TODO - move to Dockerfile
    private static final ArrayList<String> hosts = new ArrayList<>(Arrays.asList(
            "ec2-3-120-244-50.eu-central-1.compute.amazonaws.com",
            "ec2-3-120-209-6.eu-central-1.compute.amazonaws.com"));

    /**
     * Main function for Springboot application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * A bean that holds all other hosts
     *
     * @return
     */
    @Bean
    public List<String> otherHosts() {
        return hosts.stream()
                .filter(host -> !InetAddressUtils.isLocalHost(host))
                .collect(Collectors.toList());
    }
}

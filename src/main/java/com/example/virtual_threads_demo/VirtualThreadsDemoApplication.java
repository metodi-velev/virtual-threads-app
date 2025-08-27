package com.example.virtual_threads_demo;

// VirtualThreadsDemoApplication.java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

@SpringBootApplication
public class VirtualThreadsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualThreadsDemoApplication.class, args);
    }
}

@RestController
class StatsController {

    @GetMapping("/stats")
    public String getThreadStats() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        return String.format("""
            Thread Stats:
            - Active Threads: %d
            - Peak Thread Count: %d
            - Virtual Threads Supported: %s
            """,
                threadBean.getThreadCount(),
                threadBean.getPeakThreadCount(),
                Thread.currentThread().isVirtual()
        );
    }
}

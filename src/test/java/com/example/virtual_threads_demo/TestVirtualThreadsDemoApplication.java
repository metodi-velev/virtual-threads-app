package com.example.virtual_threads_demo;

import org.springframework.boot.SpringApplication;

public class TestVirtualThreadsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(VirtualThreadsDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

package com.example.BTL_CNPM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BtlCnpmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtlCnpmApplication.class, args);
	}

}

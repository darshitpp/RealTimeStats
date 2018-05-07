package com.darshit.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.darshit")
@SpringBootApplication
public class RealTimeStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealTimeStatsApplication.class, args);
	}
}

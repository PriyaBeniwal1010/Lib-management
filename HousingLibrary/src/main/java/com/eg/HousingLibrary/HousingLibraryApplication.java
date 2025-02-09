package com.eg.HousingLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = "com.eg.HousingLibrary")
@EnableScheduling
public class HousingLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousingLibraryApplication.class, args);
		}
}



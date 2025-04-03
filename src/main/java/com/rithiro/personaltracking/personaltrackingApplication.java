package com.rithiro.personaltracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rithiro.personaltracking.configs.helpers.RSAKeyProperties;

@EnableScheduling
@ConfigurationPropertiesScan
@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class personaltrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(personaltrackingApplication.class, args);
	}

}

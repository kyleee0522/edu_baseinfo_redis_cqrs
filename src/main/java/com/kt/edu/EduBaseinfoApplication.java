package com.kt.edu;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableEncryptableProperties
@EnableCaching
@EnableFeignClients
@SpringBootApplication
@ComponentScan("com.kt")
public class EduBaseinfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduBaseinfoApplication.class, args);
	}

}


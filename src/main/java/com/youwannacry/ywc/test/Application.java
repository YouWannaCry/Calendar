package com.youwannacry.ywc.test;

import ch.qos.logback.classic.BasicConfigurator;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Application {
	@Value("$spring.datasource.url")
	private String url;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

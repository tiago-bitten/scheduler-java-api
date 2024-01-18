package com.labi.schedulerjava;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchedulerJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerJavaApplication.class, args);
	}

	@Autowired
	private MinistryRepository ministryRepository;

	@Bean
	public Object createMinistry() {
		Ministry ministry = new Ministry("CEIA", "Ceia principal", "#494949");

		ministryRepository.save(ministry);

		return null;
	}
}

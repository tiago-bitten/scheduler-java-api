package com.labi.schedulerjava;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SchedulerJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerJavaApplication.class, args);
	}

	@Autowired
	private MinistryRepository ministryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMinistryService userMinistryService;

	@Bean
	public Object createMinistry() {
		Ministry ministry = new Ministry("CEIA", "Ceia principal", "#494949");
		ministryRepository.save(ministry);
		return null;
	}

	@Bean
	public Object createUser() {
		User user = new User("Admin", "admin@admin.com", "admin01");
		user.setIsSuperUser(true);
		user.setIsApproved(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		userMinistryService.associate(user, List.of(1L));
		return null;
	}
}

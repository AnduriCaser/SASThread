package com.sast.sastthread;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sast.sastthread.model.Role;
import com.sast.sastthread.repository.RoleRepository;

@SpringBootApplication
public class SastthreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SastthreadApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			Role role = new Role();
			role.setId(0L);
			role.setName("USER");
			roleRepository.save(role);
		};
	}

}

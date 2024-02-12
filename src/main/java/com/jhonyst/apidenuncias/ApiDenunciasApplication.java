package com.jhonyst.apidenuncias;

import com.jhonyst.apidenuncias.model.ERole;
import com.jhonyst.apidenuncias.model.RoleEntity;
import com.jhonyst.apidenuncias.model.UserEntity;
import com.jhonyst.apidenuncias.repository.RoleRepository;
import com.jhonyst.apidenuncias.repository.UserRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class ApiDenunciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDenunciasApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {

			List<RoleEntity> roles = (List<RoleEntity>) roleRepository.findAll();

			if(roles.isEmpty()){
				RoleEntity roleEntity = RoleEntity.builder()
						.nameRole(ERole.ADMIN)
						.build();

				roleRepository.save(roleEntity);

				RoleEntity roleEntity2 = RoleEntity.builder()
						.nameRole(ERole.FISCAL)
						.build();

				roleRepository.save(roleEntity2);

				RoleEntity roleEntity3 = RoleEntity.builder()
						.nameRole(ERole.USER)
						.build();

				roleRepository.save(roleEntity3);
			}

			roles = (List<RoleEntity>) roleRepository.findAll();

			for(RoleEntity role : roles){
				System.out.println(role.getNameRole());
			}

			Optional<RoleEntity> optionalRole = roleRepository.getRoleByName(ERole.ADMIN);
			RoleEntity roleEntity = optionalRole.get();

			Set<RoleEntity> roleEntitySet = new HashSet<>();
			roleEntitySet.add(roleEntity);


			if(userRepository.getByUsername("admin").isEmpty()){
				UserEntity userEntity = UserEntity.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin"))
						.roles(roleEntitySet)
						.build();
				userRepository.save(userEntity);
			}




		};
	}

}

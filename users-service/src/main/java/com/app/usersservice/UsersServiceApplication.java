package com.app.usersservice;

import com.app.usersservice.model.Privilege;
import com.app.usersservice.model.Role;
import com.app.usersservice.repository.PrivilegeRepository;
import com.app.usersservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
public class UsersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CommandLineRunner defineAdminRole(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        return args -> {
            Role adminRole = Role.builder().roleName("ROLE_ADMIN").privileges(new HashSet<>()).build();
            Privilege adminCanAllPrivilege = Privilege.builder().privilegeName("ACCESS_ALL").roles(new HashSet<>()).build();

            adminRole.addPrivilege(adminCanAllPrivilege);


            if (roleRepository.findByRoleName(adminRole.getRoleName()).isEmpty()) {
                roleRepository.save(adminRole);
            }
        };
    }
}

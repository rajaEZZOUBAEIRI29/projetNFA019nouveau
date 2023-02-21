package com.cnam.demo.projetGestionRestau.entity;

import com.cnam.demo.projetGestionRestau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


   @Value("${default.email}")
    private String defaultEmail;

    @Value("${default.password}")
    private String defaultPassword;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode(defaultPassword));
            user.setRole(Role.ADMIN); // Remplacez Role.ADMIN par le rôle souhaité
            user.setNom("admin");
            user.setPrenom("admin");
            userRepository.save(user);
            System.out.println("Default user created with email: " + defaultEmail + " and password: " + defaultPassword);
        }
    }
}


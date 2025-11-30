package com.project.demo.unit_test;

import com.project.demo.logic.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@EntityScan("com.project.demo.logic.entity.user")
@EnableJpaRepositories("com.project.demo.logic.entity.user")
class UserTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    void findUserByName() {
        assertTrue((BooleanSupplier) userRepository.findUserByName("Ashely"));
    }

}

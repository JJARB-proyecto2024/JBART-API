package com.project.demo.unit_test;

import com.project.demo.logic.entity.enums.RoleEnum;
import com.project.demo.logic.entity.enums.StatusEnum;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    private User user;

    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_PASSWORD = "password";

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String email = TEST_EMAIL;
        String password = TEST_PASSWORD;
        String name = "Andrey";

        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setStatus(StatusEnum.ACTIVE);


        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(StatusEnum.ACTIVE, user.getStatus());
    }


    @Test
    void testUserDetailsMethods() {
        user.setEmail(TEST_EMAIL);

        assertEquals(TEST_EMAIL, user.getEmail());

        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }


    @Test
    void testAuthorities() {
        Role role = new Role();
        role.setName(RoleEnum.USER);

        user.setRole(role);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());

        String authorityName = authorities.iterator().next().getAuthority();
        assertEquals("ROLE_USER", authorityName);
    }

}

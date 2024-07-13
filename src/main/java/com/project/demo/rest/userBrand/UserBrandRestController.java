package com.project.demo.rest.userBrand;

import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.project.demo.logic.entity.rol.RoleRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usersBrand")
public class UserBrandRestController {
    @Autowired
    private UserBrandRepository UserBrandRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<UserBrand> getAllUsers() {
        return UserBrandRepository.findAll();
    }

    @PostMapping
    public UserBrand addUser(@RequestBody UserBrand user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER_BRAND);

        if (optionalRole.isEmpty()) {
            return null;
        }
        user.setRole(optionalRole.get());

        return UserBrandRepository.save(user);
    }

    @GetMapping("/{id}")
    public UserBrand getUserById(@PathVariable Long id) {
        return UserBrandRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/filterByName/{name}")
    public List<UserBrand> getUserById(@PathVariable String name) {
        return UserBrandRepository.findUsersWithCharacterInName(name);
    }

    @PutMapping("/{id}")
    public UserBrand updateUser(@PathVariable Long id, @RequestBody UserBrand user) {
        return UserBrandRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setLogoType(user.getLogoType());
                    existingUser.setMainLocationAddress(user.getMainLocationAddress());
                    existingUser.setBrandCategories(user.getBrandCategories());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());

                    return UserBrandRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return UserBrandRepository.save(user);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        UserBrandRepository.deleteById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserBrand authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserBrand) authentication.getPrincipal();
    }

}
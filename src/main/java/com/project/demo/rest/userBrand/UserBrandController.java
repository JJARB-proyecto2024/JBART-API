package com.project.demo.rest.userBrand;


import com.project.demo.logic.entity.userBrand.UserBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userBrand")
public class UserBrandController {

    @Autowired
    private UserBrandService sevice;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAllUserBrand() {
        return ResponseEntity.status(HttpStatus.OK).body(sevice.getAllUserBrand());
    }

}

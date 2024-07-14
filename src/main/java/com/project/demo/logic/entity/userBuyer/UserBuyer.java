package com.project.demo.logic.entity.userBuyer;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name = "user_buyer")
public class UserBuyer extends User{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @CreationTimestamp
//    @Column(updatable = false, name = "created_at")
//    private Date createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private Date updatedAt;
//
//    public UserBuyer() {}
//
//    // Métodos heredados
//
//
//    @Override
//    public String getUsername() {
//        return getEmail(); // getEmail() heredado
//    }
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Override
//    public void setName(String name) {
//        super.setName(name); // setName() de la clase User
//    }
//
//    @Override
//    public void setLastname(String lastName) {
//        super.setLastname(lastName); // setlastName() de la clase User
//    }
//
//    @Override
//    public void setEmail(String email) {
//        super.setEmail(email); //  setEmail() de la clase User
//    }
//
//    @Override
//    public void setPassword(String password) {
//        super.setPassword(password); // setPassword() de la clase User
//    }
//
//    @Override
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    @Override
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    @Override
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    @Override
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
}

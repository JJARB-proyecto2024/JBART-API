package com.project.demo.logic.entity.userBrand;

import com.project.demo.logic.entity.rol.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "brand_user")
public class UserBrand implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "legal_id", nullable = false, unique = true, length = 9)
    private Long legalId;

    @Column(name = "logo_type", nullable = false)
    private String logoType;

    @Column(name = "brand_name", nullable = false, unique = true)
    private String brandName;

    @Column(name = "legal_representative_name", nullable = false)
    private String legalRepresentativeName;

    @Column(name = "main_location_address", nullable = false)
    private String mainLocationAddress;

    @Lob
    @Column(name = "legal_documents")
    private byte[] legalDocuments;

    @Column(name = "brand_categories", nullable = false)
    private String brandCategories;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    public UserBrand() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLegalId() {
        return legalId;
    }

    public void setLegalId(Long legalId) {
        this.legalId = legalId;
    }

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(String logoType) {
        this.logoType = logoType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLegalRepresentativeName() {
        return legalRepresentativeName;
    }

    public void setLegalRepresentativeName(String legalRepresentativeName) {
        this.legalRepresentativeName = legalRepresentativeName;
    }

    public String getMainLocationAddress() {
        return mainLocationAddress;
    }

    public void setMainLocationAddress(String mainLocationAddress) {
        this.mainLocationAddress = mainLocationAddress;
    }

    public byte[] getLegalDocuments() {
        return legalDocuments;
    }

    public void setLegalDocuments(byte[] legalDocuments) {
        this.legalDocuments = legalDocuments;
    }

    public String getBrandCategories() {
        return brandCategories;
    }

    public void setBrandCategories(String brandCategories) {
        this.brandCategories = brandCategories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public UserBrand setRole(Role role) {
        this.role = role;
        return this;
    }
}

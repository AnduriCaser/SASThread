package com.sast.sastthread.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @JsonProperty("email")
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "attempt", unique = true, nullable = false)
    @JsonProperty("attempt")
    private int attempt;

    @Column(name = "status", unique = true)
    @JsonProperty("status")
    private String status;

    @Column(name = "slug", columnDefinition = "VARCHAR(255) DEFAULT (UUID())")
    @JsonProperty("slug")
    private String slug;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonProperty("roles")
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_products", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonProperty("products")
    private Set<Product> products;

    @Column(name = "created_at")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    @JsonProperty("lastLoginAt")
    private LocalDateTime lastLoginAt;

    @Column(name = "current_login_at")
    @JsonProperty("currentLoginAt")
    private LocalDateTime currentLoginAt;

    @Column(name = "last_login_ip")
    @JsonProperty("lastLoginIp")
    private String lastLoginIp;

    @Column(name = "current_login_ip")
    @JsonProperty("currentLoginIp")
    private String currentLoginIp;

    @Column(name = "login_count")
    @JsonProperty("loginCount")
    private Long loginCount;

    @Column(name = "active")
    @JsonProperty("active")
    private Boolean active;

}

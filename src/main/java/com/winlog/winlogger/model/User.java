package com.winlog.winlogger.model;

import jakarta.persistence.*;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        }
)

public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 50)
        private String username;

        @Column(nullable = false, length = 255)
        private String password; // store ONLY BCrypt hashes here

        @Column(length = 150)
        private String email;

        // Simple, flexible role storage (e.g., ROLE_USER, ROLE_ADMIN)
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id")
        )
        @Column(name = "role", nullable = false, length = 50)
        private Set<String> roles = new HashSet<>();

        @Column(nullable = false)
        private boolean enabled = true; // future-proofing: disable/lock users

        @Column(nullable = false, updatable = false)
        private Instant createdAt;

        @PrePersist
        void onCreate() {
                this.createdAt = Instant.now();
        }


        // === Getters & Setters ===
        public Long getId() { return id; }

        public void setId(Long id) { this.id = id; }

        public String getUsername() { return username; }

        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }

        public void setPassword(String password) { this.password = password; }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }

        public Set<String> getRoles() { return roles; }

        public void setRoles(Set<String> roles) { this.roles = roles; }

        public boolean isEnabled() { return enabled; }

        public void setEnabled(boolean enabled) { this.enabled = enabled; }

        public Instant getCreatedAt() { return createdAt; }

        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
package com.sast.sastthread.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    public Product(String name, String description, String risk_level) {
        this.name = name;
        this.description = description;
        this.risk_level = risk_level;
        this.slug = UUID.randomUUID().toString();
    }

    @Id
    @JsonProperty(value = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "slug", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT (UUID())")
    @JsonProperty("slug")
    private String slug;

    @Column(name = "risk_level")
    @JsonProperty("risk_Level")
    private String risk_level;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_findings", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "finding_id"))
    @JsonProperty("findings")
    private Set<Finding> findings;

    @Column(name = "created_at")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}

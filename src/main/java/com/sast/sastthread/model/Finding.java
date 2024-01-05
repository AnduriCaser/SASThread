package com.sast.sastthread.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "finding")
public class Finding {

    public Finding(String title, String severity, List<String> endpoints) {
        this.slug = UUID.randomUUID().toString();
        this.title = title;
        this.severity = severity;
        this.endpoints = endpoints;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "title", length = 511)
    @JsonProperty("title")
    private String title;

    @Column(name = "severity")
    @JsonProperty("severity")
    private String severity;

    @Column(name = "vulnerable_code")
    @JsonProperty("vulnerable_code")
    private String vulnerableCode;

    @Column(name = "slug", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT (UUID())")
    @JsonProperty("slug")
    private String slug;

    @Column(name = "endpoints")
    @JsonProperty("endpoints")
    private List<String> endpoints;

    @Column(name = "created_at")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

}

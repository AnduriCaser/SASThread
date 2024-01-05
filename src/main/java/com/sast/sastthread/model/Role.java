package com.sast.sastthread.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @JsonProperty(value = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @Column(name = "slug", columnDefinition = "VARCHAR(255) DEFAULT (UUID())")
    @JsonProperty("slug")
    private String slug;

    @JsonProperty(value = "description")
    private String description;

}

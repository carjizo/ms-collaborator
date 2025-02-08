package com.colaborator.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "collaborator")
public class Collaborator {

    @Id
    @Size(max = 25)
    @NotBlank
    @Column(name = "id_document", unique = true)
    private String idDocument;

    @Size(max = 100)
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 50)
    @Column(name = "phone")
    private String phone;

    @NotBlank
    @Size(max = 20)
    private String role;

    private boolean status;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        ZoneId zoneId = ZoneId.of("America/Lima");
        createdAt = OffsetDateTime.now(zoneId);
        updatedAt = OffsetDateTime.now(zoneId);
    }

    @PreUpdate
    protected void onUpdate() {
        ZoneId zoneId = ZoneId.of("America/Lima");
        updatedAt = OffsetDateTime.now(zoneId);
    }

}

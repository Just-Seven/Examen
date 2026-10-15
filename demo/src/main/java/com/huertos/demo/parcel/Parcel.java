package com.huertos.demo.parcel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "parcels")
public class Parcel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private Location location;
    @Enumerated(EnumType.STRING)
    private SoilType soilType;
    private String ownerOrLead;
    private Instant createdAt;

    protected Parcel() { }
    public Parcel(String name, Location location, SoilType soilType, String ownerOrLead) {
        this.name = name; this.location = location; this.soilType = soilType; this.ownerOrLead = ownerOrLead;
    }
    @PrePersist
    void onCreate() { if (createdAt == null) createdAt = Instant.now(); }
    public Long getId() { return id; }
    public String getName() { return name; }
    public Location getLocation() { return location; }
    public SoilType getSoilType() { return soilType; }
    public String getOwnerOrLead() { return ownerOrLead; }
    public Instant getCreatedAt() { return createdAt; }
}

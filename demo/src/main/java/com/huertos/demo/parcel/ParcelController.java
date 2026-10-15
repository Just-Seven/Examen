package com.huertos.demo.parcel;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parcels")
public class ParcelController {
    private final ParcelService service;
    public ParcelController(ParcelService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Parcel> create(@Valid @RequestBody ParcelRequest request) {
        Parcel created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/parcels/" + created.getId())).body(created);
    }
    @GetMapping
    public List<Parcel> findAll() { return service.findAll(); }
    @GetMapping("/{id}")
    public Parcel findById(@PathVariable Long id) { return service.findById(id); }
}

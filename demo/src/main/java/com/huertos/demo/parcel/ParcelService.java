package com.huertos.demo.parcel;

import java.text.Normalizer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParcelService {
    private final ParcelRepository repository;
    public ParcelService(ParcelRepository repository) { this.repository = repository; }

    public Parcel create(ParcelRequest request) {
        return repository.save(new Parcel(sanitize(request.name()), request.location(), request.soilType(), sanitize(request.ownerOrLead())));
    }
    @Transactional(readOnly = true)
    public List<Parcel> findAll() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Parcel findById(Long id) { return repository.findById(id).orElseThrow(() -> new ParcelNotFoundException(id)); }

    private String sanitize(String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFKC);
        return normalized.replaceAll("<[^>]*>", "").replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "").trim();
    }
}

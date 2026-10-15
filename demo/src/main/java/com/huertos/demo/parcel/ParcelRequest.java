package com.huertos.demo.parcel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParcelRequest(
    @NotBlank(message = "El nombre de la parcela es obligatorio")
    @Size(max = 120, message = "El nombre no puede exceder 120 caracteres") String name,
    @NotNull(message = "La ubicación es obligatoria") @Valid Location location,
    @NotNull(message = "El tipo de suelo es obligatorio") SoilType soilType,
    @NotBlank(message = "El responsable es obligatorio")
    @Size(max = 120, message = "El responsable no puede exceder 120 caracteres") String ownerOrLead
) { }

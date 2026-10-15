package com.huertos.demo.parcel;

public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException(Long id) { super("No se encontró la parcela con id " + id); }
}

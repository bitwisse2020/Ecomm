package com.example.common.Models.service_user;

public interface BaseAddress {
    String getStreet();

    String getCity();

    String getState();

    String getZipCode();

    String getCountry();

    boolean isDefault();
}

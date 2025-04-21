package com.example.product_service;

import com.example.common.Models.ErrorResponse; // Your problematic import

public class DebugPaths {
    public static void main(String[] args) {
        try {
            Class<?> clazz = ErrorResponse.class;
            System.out.println("Class loaded from: " +
                    clazz.getProtectionDomain()
                            .getCodeSource()
                            .getLocation());
        } catch (Exception e) {
            System.err.println("ErrorResponse class not found!");
            e.printStackTrace();
        }
    }
}
package com.example.product_service.Utility;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugGenerator {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String generateSlug(String input) {
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String appendSuffix(String baseSlug, int suffix) {
        if (baseSlug == null || baseSlug.isEmpty()) {
            // Return just the suffix or handle error appropriately based on requirements
            return String.valueOf(suffix);
        }
        // Appends the suffix number with a preceding hyphen
        return baseSlug + "-" + suffix;
    }
}


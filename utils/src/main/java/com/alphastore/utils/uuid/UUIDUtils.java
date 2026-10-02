package com.alphastore.utils.uuid;

import com.alphastore.utils.validation.ValidationUtils;

import java.util.UUID;
import java.util.regex.Pattern;

public final class UUIDUtils {

    // 8-4-4-4-12 hex characters, e.g. 3f2b8c1e-9a4d-4e2f-b6a1-7c5d9e0f1a2b
    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private UUIDUtils() {
    }

    /** generates a new random UUID as a string */
    public static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    /** true if the value is a properly formatted UUID */
    public static boolean isValidUUID(String value) {
        if (ValidationUtils.isNullOrEmpty(value)) {
            return false;
        }
        return UUID_PATTERN.matcher(value.trim()).matches();
    }
}

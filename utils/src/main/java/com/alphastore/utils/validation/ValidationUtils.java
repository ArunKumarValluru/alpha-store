package com.alphastore.utils.validation;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern ALPHABETIC_PATTERN = Pattern.compile("^[A-Za-z ]+$");
    // min 8 chars, at least one uppercase, one lowercase, one digit and one special character, no spaces
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,64}$");

    private ValidationUtils() {
    }

    // ---------------- String ----------------

    /** true if the string is null, empty, or only spaces */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNotNullOrEmpty(String value) {
        return !isNullOrEmpty(value);
    }

    /** true if the trimmed length is between min and max (inclusive) */
    public static boolean hasLength(String value, int min, int max) {
        if (value == null) {
            return false;
        }
        int length = value.trim().length();
        return length >= min && length <= max;
    }

    public static boolean isNumeric(String value) {
        return value != null && NUMERIC_PATTERN.matcher(value).matches();
    }

    public static boolean isAlphabetic(String value) {
        return isNotNullOrEmpty(value) && ALPHABETIC_PATTERN.matcher(value).matches();
    }

    public static boolean isValidEmail(String value) {
        return value != null && EMAIL_PATTERN.matcher(value.trim()).matches();
    }

    /** 10-digit Indian mobile number starting with 6-9 */
    public static boolean isValidMobile(String value) {
        return value != null && MOBILE_PATTERN.matcher(value.trim()).matches();
    }

    /** 8-64 chars with at least one uppercase, lowercase, digit and special character */
    public static boolean isValidPassword(String value) {
        return value != null && PASSWORD_PATTERN.matcher(value).matches();
    }

    // ---------------- List / Collection ----------------

    public static boolean isNullOrEmpty(Collection<?> values) {
        return values == null || values.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Collection<?> values) {
        return !isNullOrEmpty(values);
    }

    /** true if the list is non-empty and none of its elements are null */
    public static boolean hasNoNullElements(Collection<?> values) {
        return isNotNullOrEmpty(values) && values.stream().noneMatch(Objects::isNull);
    }

    /** true if the list is non-empty and none of its strings are null/blank */
    public static boolean hasNoEmptyStrings(Collection<String> values) {
        return isNotNullOrEmpty(values) && values.stream().noneMatch(ValidationUtils::isNullOrEmpty);
    }

    public static boolean hasSize(Collection<?> values, int min, int max) {
        int size = values == null ? 0 : values.size();
        return size >= min && size <= max;
    }

    // ---------------- Map ----------------

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }

    /** true if the map has every given key */
    public static boolean containsKeys(Map<?, ?> map, Object... keys) {
        if (isNullOrEmpty(map) || keys == null) {
            return false;
        }
        for (Object key : keys) {
            if (!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    /** true if the map has every given key and none of their values are null (or blank, for strings) */
    public static boolean hasNonEmptyValues(Map<?, ?> map, Object... keys) {
        if (!containsKeys(map, keys)) {
            return false;
        }
        for (Object key : keys) {
            Object value = map.get(key);
            if (value == null || (value instanceof String && isNullOrEmpty((String) value))) {
                return false;
            }
        }
        return true;
    }
}

package com.burpai.utils;

import java.util.regex.Pattern;

/**
 * Utility for masking sensitive data before sending to external APIs
 */
public class DataMasker {

    /**
     * Mask sensitive headers in HTTP request/response
     */
    public static String maskSensitiveData(String httpData) {
        if (httpData == null || httpData.isEmpty()) {
            return httpData;
        }

        String masked = httpData;

        // Mask Authorization headers
        masked = maskHeader(masked, "Authorization", "Authorization: [MASKED]");

        // Mask Cookie headers
        masked = maskHeader(masked, "Cookie", "Cookie: [MASKED]");

        // Mask Set-Cookie headers
        masked = maskHeader(masked, "Set-Cookie", "Set-Cookie: [MASKED]");

        // Mask X-API-Key headers
        masked = maskHeader(masked, "X-API-Key", "X-API-Key: [MASKED]");
        masked = maskHeader(masked, "x-api-key", "x-api-key: [MASKED]");

        // Mask X-Auth headers
        masked = maskHeader(masked, "X-Auth", "X-Auth: [MASKED]");

        // Mask API Key in URLs/params (basic pattern matching)
        masked = masked.replaceAll("(?i)(api[_-]?key)=([^&\\s]+)", "$1=[MASKED]");
        masked = masked.replaceAll("(?i)(token)=([^&\\s]+)", "$1=[MASKED]");
        masked = masked.replaceAll("(?i)(password)=([^&\\s]+)", "$1=[MASKED]");

        return masked;
    }

    /**
     * Mask a specific header value
     */
    private static String maskHeader(String data, String headerName, String replacement) {
        // Case-insensitive regex to find and replace header values
        Pattern pattern = Pattern.compile("(?i)(?:^|\\r?\\n)(" + Pattern.quote(headerName) + "):\\s*[^\\r\\n]+");
        return pattern.matcher(data).replaceAll("\n" + replacement);
    }

    /**
     * Check if data appears to contain sensitive information
     */
    public static boolean containsSensitivePatterns(String data) {
        if (data == null) return false;

        // Check for common sensitive patterns
        return data.toLowerCase().contains("authorization") ||
                data.toLowerCase().contains("api-key") ||
                data.toLowerCase().contains("api_key") ||
                data.toLowerCase().contains("password") ||
                data.toLowerCase().contains("token") ||
                data.toLowerCase().contains("cookie") ||
                data.toLowerCase().contains("bearer ");
    }
}

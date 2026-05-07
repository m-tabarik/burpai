package com.burpai.utils;

/**
 * Utility for parsing and formatting AI analysis responses
 */
public class ResponseParser {

    /**
     * Format analysis results for display
     */
    public static String formatAnalysis(String raw) {
        if (raw == null || raw.isEmpty()) {
            return "No analysis results";
        }

        // The response is already well-formatted by the AI
        // Just add a wrapper and ensure proper formatting
        StringBuilder formatted = new StringBuilder();
        formatted.append("═══════════════════════════════════════════════════════════\n");
        formatted.append("                  BURPAI PRO ANALYSIS RESULTS\n");
        formatted.append("═══════════════════════════════════════════════════════════\n\n");
        formatted.append(raw);
        formatted.append("\n\n═══════════════════════════════════════════════════════════\n");

        return formatted.toString();
    }

    /**
     * Truncate response to a maximum length
     */
    public static String truncateResponse(String response, int maxChars) {
        if (response == null) return "";
        if (response.length() <= maxChars) return response;

        return response.substring(0, maxChars) +
                "\n\n[Response truncated - too long. Consider splitting your request or analyzing specific parts.]";
    }

    /**
     * Clean up response text
     */
    public static String cleanResponse(String response) {
        if (response == null) return "";

        // Remove excessive whitespace
        response = response.replaceAll("\\n\\n\\n+", "\n\n");
        response = response.trim();

        return response;
    }

    /**
     * Extract and highlight vulnerabilities from response
     */
    public static String highlightVulnerabilities(String response) {
        if (response == null || response.isEmpty()) {
            return response;
        }

        // Simple highlighting - wrap vulnerability types in markers
        String highlighted = response;

        String[] vulnTypes = {
                "SQL Injection", "sql injection", "XSS", "Cross-Site Scripting",
                "CSRF", "IDOR", "Broken Access Control", "Authentication",
                "Authorization", "Information Disclosure", "Sensitive Data",
                "Command Injection", "Path Traversal", "XXE", "Deserialization"
        };

        for (String vuln : vulnTypes) {
            highlighted = highlighted.replaceAll(
                    "(?i)" + vuln,
                    "[!] " + vuln + " [!]"
            );
        }

        return highlighted;
    }

    /**
     * Check if response contains error indication
     */
    public static boolean isErrorResponse(String response) {
        if (response == null) return false;
        String lower = response.toLowerCase();
        return lower.contains("error") ||
                lower.contains("exception") ||
                lower.contains("failed") ||
                lower.contains("unable to");
    }
}

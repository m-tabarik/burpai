package com.burpai.api;

import org.apache.log4j.Logger;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * HTTP Client for making API requests to OpenAI and Claude
 */
public class HttpClient {
    private static final int TIMEOUT_MS = 30000; // 30 second timeout
    private static final int RETRY_COUNT = 2;
    private static final Logger logger = Logger.getLogger(HttpClient.class);

    /**
     * Make a POST request to the specified URL
     */
    public static APIResponse post(String urlString, String jsonPayload, String apiKey, String provider) {
        logger.info("[HttpClient] POST request to: " + urlString);
        logger.info("[HttpClient] Provider: " + provider);
        logger.debug("[HttpClient] Payload length: " + jsonPayload.length() + " bytes");

        for (int attempt = 0; attempt <= RETRY_COUNT; attempt++) {
            try {
                logger.info("[HttpClient] Attempt " + (attempt + 1) + " of " + (RETRY_COUNT + 1));

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(TIMEOUT_MS);
                connection.setReadTimeout(TIMEOUT_MS);

                // Set headers
                connection.setRequestProperty("Content-Type", "application/json");
                if ("openai".equalsIgnoreCase(provider)) {
                    connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                    logger.debug("[HttpClient] Set OpenAI authorization header");
                } else if ("claude".equalsIgnoreCase(provider)) {
                    connection.setRequestProperty("x-api-key", apiKey);
                    connection.setRequestProperty("anthropic-version", "2023-06-01");
                    logger.debug("[HttpClient] Set Claude headers (x-api-key, anthropic-version)");
                }

                // Write payload
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                logger.debug("[HttpClient] Payload sent");

                // Read response
                int statusCode = connection.getResponseCode();
                String response = readResponse(connection);
                connection.disconnect();

                logger.info("[HttpClient] Response status: " + statusCode);
                logger.debug("[HttpClient] Response length: " + response.length() + " bytes");
                if (statusCode >= 400) {
                    logger.error("[HttpClient] Error response: " + response.substring(0, Math.min(500, response.length())));
                }

                return new APIResponse(statusCode, response, null);

            } catch (Exception e) {
                logger.error("[HttpClient] Attempt " + (attempt + 1) + " failed: " + e.getMessage());
                if (attempt < RETRY_COUNT) {
                    try {
                        long sleepTime = 1000 * (attempt + 1);
                        logger.info("[HttpClient] Waiting " + sleepTime + "ms before retry...");
                        Thread.sleep(sleepTime); // Exponential backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    logger.error("[HttpClient] All retries failed: " + e.getMessage());
                    return new APIResponse(0, null, e.getMessage());
                }
            }
        }
        logger.error("[HttpClient] Max retries exceeded");
        return new APIResponse(0, null, "Max retries exceeded");
    }

    /**
     * Read the response body from HTTP connection
     */
    private static String readResponse(HttpURLConnection connection) throws IOException {
        InputStream is = connection.getResponseCode() >= 400 ?
                connection.getErrorStream() : connection.getInputStream();

        if (is == null) {
            return "";
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    /**
     * Truncate text to a maximum number of characters
     */
    public static String truncateText(String text, int maxChars) {
        if (text == null) return "";
        if (text.length() <= maxChars) return text;
        return text.substring(0, maxChars) + "\n... [truncated]";
    }
}

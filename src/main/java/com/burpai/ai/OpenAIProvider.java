package com.burpai.ai;

import com.burpai.api.APIResponse;
import com.burpai.api.HttpClient;
import com.burpai.models.AnalysisRequest;
import com.burpai.models.AnalysisResult;
import com.burpai.models.ExtensionSettings;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * OpenAI GPT provider for AI analysis
 */
public class OpenAIProvider implements AIProvider {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final Logger logger = Logger.getLogger(OpenAIProvider.class);
    private ExtensionSettings settings;

    public OpenAIProvider(ExtensionSettings settings) {
        this.settings = settings;
    }

    @Override
    public AnalysisResult analyze(AnalysisRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("[OpenAI] Starting analysis...");

        try {
            if (!isConfigured()) {
                logger.error("[OpenAI] API key not configured");
                return new AnalysisResult(
                        null,
                        false,
                        "OpenAI API key not configured",
                        System.currentTimeMillis() - startTime
                );
            }

            logger.info("[OpenAI] Building payload with model: " + settings.getOpenAiModel());
            // Build the request payload
            JSONObject payload = buildPayload(request);
            logger.debug("[OpenAI] Payload: " + payload.toString().substring(0, Math.min(200, payload.toString().length())) + "...");

            logger.info("[OpenAI] Sending API request to: " + API_ENDPOINT);
            // Make the API call
            APIResponse response = HttpClient.post(
                    API_ENDPOINT,
                    payload.toString(),
                    settings.getOpenAiApiKey(),
                    "openai"
            );

            logger.info("[OpenAI] Response status: " + response.getStatusCode());

            // Check for errors
            if (!response.isSuccess()) {
                String error = extractError(response);
                logger.error("[OpenAI] API error: " + error);
                logger.debug("[OpenAI] Response body: " + response.getBody());
                return new AnalysisResult(
                        null,
                        false,
                        "OpenAI API Error: " + error,
                        System.currentTimeMillis() - startTime
                );
            }

            logger.info("[OpenAI] Parsing response...");
            // Parse the response
            String analysis = parseResponse(response.getBody());
            logger.info("[OpenAI] Analysis completed successfully");
            return new AnalysisResult(
                    analysis,
                    true,
                    null,
                    System.currentTimeMillis() - startTime
            );

        } catch (Exception e) {
            logger.error("[OpenAI] Exception: " + e.getMessage(), e);
            return new AnalysisResult(
                    null,
                    false,
                    "Error: " + e.getMessage(),
                    System.currentTimeMillis() - startTime
            );
        }
    }

    /**
     * Build the request payload for OpenAI API
     */
    private JSONObject buildPayload(AnalysisRequest request) {
        JSONObject payload = new JSONObject();

        payload.put("model", settings.getOpenAiModel());
        payload.put("temperature", settings.getTemperature());
        payload.put("max_tokens", settings.getMaxTokens());

        // Build messages
        JSONArray messages = new JSONArray();

        // System message
        JSONObject systemMsg = new JSONObject();
        systemMsg.put("role", "system");
        systemMsg.put("content", PromptBuilder.getSystemPrompt());
        messages.put(systemMsg);

        // User message with request/response
        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", PromptBuilder.buildPrompt(
                request.getRequest(),
                request.getResponse(),
                request.getAnalysisMode()
        ));
        messages.put(userMsg);

        payload.put("messages", messages);

        return payload;
    }

    /**
     * Parse the response from OpenAI API
     */
    private String parseResponse(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            JSONArray choices = response.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");
                return message.getString("content");
            }
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
        return "No response received from OpenAI";
    }

    /**
     * Extract error message from API response
     */
    private String extractError(APIResponse response) {
        try {
            JSONObject error = new JSONObject(response.getBody());
            if (error.has("error")) {
                JSONObject errorObj = error.getJSONObject("error");
                if (errorObj.has("message")) {
                    return errorObj.getString("message");
                }
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
        return "HTTP " + response.getStatusCode();
    }

    @Override
    public boolean isConfigured() {
        String apiKey = settings.getOpenAiApiKey();
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    @Override
    public String getProviderName() {
        return "OpenAI";
    }
}

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
 * Claude (Anthropic) provider for AI analysis
 */
public class ClaudeProvider implements AIProvider {
    private static final String API_ENDPOINT = "https://api.anthropic.com/v1/messages";
    private static final Logger logger = Logger.getLogger(ClaudeProvider.class);
    private ExtensionSettings settings;

    public ClaudeProvider(ExtensionSettings settings) {
        this.settings = settings;
    }

    @Override
    public AnalysisResult analyze(AnalysisRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("[Claude] Starting analysis...");

        try {
            if (!isConfigured()) {
                logger.error("[Claude] API key not configured");
                return new AnalysisResult(
                        null,
                        false,
                        "Claude API key not configured",
                        System.currentTimeMillis() - startTime
                );
            }

            logger.info("[Claude] Building payload with model: " + settings.getClaudeModel());
            // Build the request payload
            JSONObject payload = buildPayload(request);
            logger.debug("[Claude] Payload: " + payload.toString().substring(0, Math.min(200, payload.toString().length())) + "...");

            logger.info("[Claude] Sending API request to: " + API_ENDPOINT);
            // Make the API call
            APIResponse response = HttpClient.post(
                    API_ENDPOINT,
                    payload.toString(),
                    settings.getClaudeApiKey(),
                    "claude"
            );

            logger.info("[Claude] Response status: " + response.getStatusCode());

            // Check for errors
            if (!response.isSuccess()) {
                String error = extractError(response);
                logger.error("[Claude] API error: " + error);
                logger.debug("[Claude] Response body: " + response.getBody());
                return new AnalysisResult(
                        null,
                        false,
                        "Claude API Error: " + error,
                        System.currentTimeMillis() - startTime
                );
            }

            logger.info("[Claude] Parsing response...");
            // Parse the response
            String analysis = parseResponse(response.getBody());
            logger.info("[Claude] Analysis completed successfully");
            return new AnalysisResult(
                    analysis,
                    true,
                    null,
                    System.currentTimeMillis() - startTime
            );

        } catch (Exception e) {
            logger.error("[Claude] Exception: " + e.getMessage(), e);
            return new AnalysisResult(
                    null,
                    false,
                    "Error: " + e.getMessage(),
                    System.currentTimeMillis() - startTime
            );
        }
    }

    /**
     * Build the request payload for Claude API
     */
    private JSONObject buildPayload(AnalysisRequest request) {
        JSONObject payload = new JSONObject();

        payload.put("model", settings.getClaudeModel());
        payload.put("max_tokens", settings.getMaxTokens());

        // Build messages - Claude uses a single user message format
        JSONArray messages = new JSONArray();

        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");

        // Combine system prompt with user prompt for Claude
        String systemPrompt = PromptBuilder.getSystemPrompt();
        String userPrompt = PromptBuilder.buildPrompt(
                request.getRequest(),
                request.getResponse(),
                request.getAnalysisMode()
        );

        userMsg.put("content", systemPrompt + "\n\n" + userPrompt);
        messages.put(userMsg);

        payload.put("messages", messages);

        return payload;
    }

    /**
     * Parse the response from Claude API
     */
    private String parseResponse(String jsonResponse) {
        try {
            JSONObject response = new JSONObject(jsonResponse);
            JSONArray content = response.getJSONArray("content");
            if (content.length() > 0) {
                JSONObject firstContent = content.getJSONObject(0);
                if (firstContent.has("text")) {
                    return firstContent.getString("text");
                }
            }
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
        return "No response received from Claude";
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
        String apiKey = settings.getClaudeApiKey();
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    @Override
    public String getProviderName() {
        return "Claude";
    }
}

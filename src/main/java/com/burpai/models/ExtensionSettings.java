package com.burpai.models;

import java.io.Serializable;

/**
 * Stores and manages extension settings.
 * This includes API keys, model selections, and analysis preferences.
 */
public class ExtensionSettings implements Serializable {
    private static final long serialVersionUID = 1L;

    private String openAiApiKey = "";
    private String openAiModel = "gpt-4o";
    private double temperature = 0.7;
    private int maxTokens = 2000;

    private String claudeApiKey = "";
    private String claudeModel = "claude-haiku-4-5-20251001";

    private String selectedProvider = "openai";
    private String analysisMode = "passive"; // passive, active, explain
    private boolean maskSensitiveData = true;
    private boolean autoAnalyze = false;

    // Getters and Setters
    public String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public void setOpenAiApiKey(String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }

    public String getOpenAiModel() {
        return openAiModel;
    }

    public void setOpenAiModel(String openAiModel) {
        this.openAiModel = openAiModel;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = Math.min(Math.max(temperature, 0), 2); // Clamp 0-2
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = Math.max(100, Math.min(maxTokens, 4000)); // Clamp 100-4000
    }

    public String getClaudeApiKey() {
        return claudeApiKey;
    }

    public void setClaudeApiKey(String claudeApiKey) {
        this.claudeApiKey = claudeApiKey;
    }

    public String getClaudeModel() {
        return claudeModel;
    }

    public void setClaudeModel(String claudeModel) {
        this.claudeModel = claudeModel;
    }

    public String getSelectedProvider() {
        return selectedProvider;
    }

    public void setSelectedProvider(String selectedProvider) {
        this.selectedProvider = selectedProvider.toLowerCase();
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode.toLowerCase();
    }

    public boolean isMaskSensitiveData() {
        return maskSensitiveData;
    }

    public void setMaskSensitiveData(boolean maskSensitiveData) {
        this.maskSensitiveData = maskSensitiveData;
    }

    public boolean isAutoAnalyze() {
        return autoAnalyze;
    }

    public void setAutoAnalyze(boolean autoAnalyze) {
        this.autoAnalyze = autoAnalyze;
    }

    /**
     * Get the currently selected API key based on the selected provider
     */
    public String getSelectedApiKey() {
        if ("claude".equalsIgnoreCase(selectedProvider)) {
            return claudeApiKey;
        }
        return openAiApiKey;
    }

    /**
     * Validate that required settings are configured
     */
    public boolean isValid() {
        String apiKey = getSelectedApiKey();
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}

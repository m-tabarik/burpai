package com.burpai.models;

/**
 * Represents an AI analysis request with HTTP request and response
 */
public class AnalysisRequest {
    private String request;
    private String response;
    private String analysisMode;
    private String provider;

    public AnalysisRequest(String request, String response, String analysisMode, String provider) {
        this.request = request;
        this.response = response;
        this.analysisMode = analysisMode;
        this.provider = provider;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(String analysisMode) {
        this.analysisMode = analysisMode;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

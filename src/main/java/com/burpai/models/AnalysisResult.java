package com.burpai.models;

/**
 * Represents the result of an AI analysis
 */
public class AnalysisResult {
    private String analysis;
    private boolean success;
    private String errorMessage;
    private long processingTimeMs;

    public AnalysisResult(String analysis, boolean success, String errorMessage, long processingTimeMs) {
        this.analysis = analysis;
        this.success = success;
        this.errorMessage = errorMessage;
        this.processingTimeMs = processingTimeMs;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    @Override
    public String toString() {
        if (!success) {
            return "Error: " + errorMessage;
        }
        return analysis + "\n\n[Processing Time: " + processingTimeMs + "ms]";
    }
}

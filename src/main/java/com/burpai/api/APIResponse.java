package com.burpai.api;

/**
 * Generic API response wrapper
 */
public class APIResponse {
    private int statusCode;
    private String body;
    private String error;

    public APIResponse(int statusCode, String body, String error) {
        this.statusCode = statusCode;
        this.body = body;
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300 && error == null;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "statusCode=" + statusCode +
                ", body='" + (body != null ? body.substring(0, Math.min(100, body.length())) : "null") + "...'" +
                ", error='" + error + '\'' +
                '}';
    }
}

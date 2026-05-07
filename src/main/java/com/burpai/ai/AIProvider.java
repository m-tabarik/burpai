package com.burpai.ai;

import com.burpai.models.AnalysisRequest;
import com.burpai.models.AnalysisResult;

/**
 * Interface for AI providers (OpenAI, Claude, etc.)
 */
public interface AIProvider {
    /**
     * Analyze the provided request and response
     */
    AnalysisResult analyze(AnalysisRequest request);

    /**
     * Check if the provider is properly configured
     */
    boolean isConfigured();

    /**
     * Get provider name
     */
    String getProviderName();
}

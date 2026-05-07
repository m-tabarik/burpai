package com.burpai.workers;

import com.burpai.ai.AIProvider;
import com.burpai.models.AnalysisRequest;
import com.burpai.models.AnalysisResult;
import com.burpai.utils.DataMasker;
import com.burpai.utils.ResponseParser;
import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * SwingWorker for async analysis processing
 * Prevents blocking the Burp UI during API calls
 */
public class AnalysisWorker extends SwingWorker<AnalysisResult, String> {
    private static final Logger logger = Logger.getLogger(AnalysisWorker.class);
    private AIProvider provider;
    private AnalysisRequest request;
    private Runnable onComplete;

    public AnalysisWorker(AIProvider provider, AnalysisRequest request, Runnable onComplete) {
        this.provider = provider;
        this.request = request;
        this.onComplete = onComplete;
    }

    @Override
    protected AnalysisResult doInBackground() throws Exception {
        logger.info("[AnalysisWorker] Starting background analysis");
        publish("Masking sensitive data...");

        try {
            logger.info("[AnalysisWorker] Provider: " + provider.getProviderName());
            logger.debug("[AnalysisWorker] Request size: " + request.getRequest().length() + " bytes");
            logger.debug("[AnalysisWorker] Response size: " + request.getResponse().length() + " bytes");

            // Mask sensitive data before analysis
            logger.info("[AnalysisWorker] Masking sensitive data...");
            String maskedRequest = DataMasker.maskSensitiveData(request.getRequest());
            String maskedResponse = DataMasker.maskSensitiveData(request.getResponse());
            logger.debug("[AnalysisWorker] Masked sizes - Request: " + maskedRequest.length() + ", Response: " + maskedResponse.length());

            publish("Sending to " + provider.getProviderName() + "...");

            // Create a new request with masked data
            AnalysisRequest maskedAnalysisRequest = new AnalysisRequest(
                    maskedRequest,
                    maskedResponse,
                    request.getAnalysisMode(),
                    request.getProvider()
            );

            logger.info("[AnalysisWorker] Calling provider.analyze()...");
            // Perform analysis
            AnalysisResult result = provider.analyze(maskedAnalysisRequest);

            logger.info("[AnalysisWorker] Analysis result - Success: " + result.isSuccess() + ", Time: " + result.getProcessingTimeMs() + "ms");

            // Format the response if successful
            if (result.isSuccess()) {
                logger.info("[AnalysisWorker] Formatting results...");
                publish("Formatting results...");
                String formatted = ResponseParser.formatAnalysis(
                        ResponseParser.cleanResponse(result.getAnalysis())
                );
                result.setAnalysis(formatted);
                logger.info("[AnalysisWorker] Formatting complete");
            } else {
                logger.error("[AnalysisWorker] Analysis failed: " + result.getErrorMessage());
            }

            logger.info("[AnalysisWorker] Background analysis complete");
            return result;

        } catch (Exception e) {
            logger.error("[AnalysisWorker] Exception during analysis: " + e.getMessage(), e);
            return new AnalysisResult(
                    null,
                    false,
                    "Worker error: " + e.getMessage(),
                    0
            );
        }
    }

    @Override
    protected void process(java.util.List<String> chunks) {
        for (String chunk : chunks) {
            // Can be used to update status messages
            System.out.println("[AnalysisWorker] " + chunk);
        }
    }

    @Override
    protected void done() {
        try {
            // Call the completion callback
            if (onComplete != null) {
                onComplete.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

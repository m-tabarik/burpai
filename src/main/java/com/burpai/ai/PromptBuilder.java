package com.burpai.ai;

/**
 * Builds prompts for security analysis based on analysis mode
 */
public class PromptBuilder {
    private static final String SYSTEM_PROMPT = "You are a professional penetration tester with expertise in cybersecurity and web application security. " +
            "Analyze the provided HTTP request and response pair to identify security vulnerabilities. " +
            "Your analysis should be concise but comprehensive, covering:\n" +
            "1. Identified vulnerabilities (e.g., IDOR, SQL injection, XSS, CSRF, broken access control, business logic flaws)\n" +
            "2. Severity level (Low, Medium, High, Critical)\n" +
            "3. Detailed explanation of the vulnerability\n" +
            "4. Impact assessment\n" +
            "5. Exploitation steps and proof of concept\n" +
            "6. Recommended remediation\n\n" +
            "Format your response with clear sections using [+] markers.";

    /**
     * Build a user prompt for passive analysis (no attack payloads)
     */
    public static String buildPassivePrompt(String request, String response) {
        return "PASSIVE ANALYSIS MODE - Identify vulnerabilities but do NOT suggest attack payloads.\n\n" +
                "HTTP Request:\n" +
                "```\n" +
                request +
                "\n```\n\n" +
                "HTTP Response:\n" +
                "```\n" +
                response +
                "\n```\n\n" +
                "Analyze the above request-response pair and identify security vulnerabilities. " +
                "Focus on explaining the issues without providing specific payload examples.";
    }

    /**
     * Build a user prompt for active analysis (includes attack suggestions)
     */
    public static String buildActivePrompt(String request, String response) {
        return "ACTIVE ANALYSIS MODE - Identify vulnerabilities AND provide exploitation steps with sample payloads.\n\n" +
                "HTTP Request:\n" +
                "```\n" +
                request +
                "\n```\n\n" +
                "HTTP Response:\n" +
                "```\n" +
                response +
                "\n```\n\n" +
                "Analyze the above request-response pair and:\n" +
                "1. Identify all security vulnerabilities\n" +
                "2. Explain each vulnerability\n" +
                "3. Provide step-by-step exploitation instructions\n" +
                "4. Include sample payloads that could be used to exploit the vulnerabilities\n" +
                "5. Suggest remediation measures";
    }

    /**
     * Build a user prompt for educational analysis (explain like beginner)
     */
    public static String buildExplainPrompt(String request, String response) {
        return "EDUCATIONAL MODE - Explain vulnerabilities in simple terms.\n\n" +
                "HTTP Request:\n" +
                "```\n" +
                request +
                "\n```\n\n" +
                "HTTP Response:\n" +
                "```\n" +
                response +
                "\n```\n\n" +
                "Analyze the above request-response pair and explain any security issues in simple, beginner-friendly language. " +
                "Assume the reader has basic networking knowledge but may not be familiar with advanced security concepts. " +
                "Use analogies where helpful. For each vulnerability found:\n" +
                "1. Explain WHAT it is\n" +
                "2. Explain WHY it's dangerous\n" +
                "3. Explain HOW it works at a high level\n" +
                "4. Suggest basic fixes";
    }

    /**
     * Get the system prompt
     */
    public static String getSystemPrompt() {
        return SYSTEM_PROMPT;
    }

    /**
     * Build the appropriate user prompt based on analysis mode
     */
    public static String buildPrompt(String request, String response, String analysisMode) {
        switch (analysisMode.toLowerCase()) {
            case "active":
                return buildActivePrompt(request, response);
            case "explain":
                return buildExplainPrompt(request, response);
            case "passive":
            default:
                return buildPassivePrompt(request, response);
        }
    }
}

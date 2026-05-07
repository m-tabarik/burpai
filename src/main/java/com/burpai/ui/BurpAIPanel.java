package com.burpai.ui;

import com.burpai.ai.AIProvider;
import com.burpai.ai.ClaudeProvider;
import com.burpai.ai.OpenAIProvider;
import com.burpai.models.AnalysisRequest;
import com.burpai.models.AnalysisResult;
import com.burpai.models.ExtensionSettings;
import com.burpai.workers.AnalysisWorker;
import burp.IHttpRequestResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Main Burp Suite UI component for BurpAI Pro
 */
public class BurpAIPanel extends JPanel {
    private ExtensionSettings settings;
    private JTextArea requestArea;
    private JTextArea responseArea;
    private JTextArea resultArea;
    private JComboBox<String> providerCombo;
    private JComboBox<String> modeCombo;
    private JButton analyzeButton;
    private JButton settingsButton;
    private JLabel statusLabel;
    private AnalysisWorker currentWorker;

    public BurpAIPanel(ExtensionSettings settings) {
        this.settings = settings;
        initializeUI();
    }

    /**
     * Initialize the UI components
     */
    private void initializeUI() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top control panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        // Middle content panel (splitter)
        JSplitPane contentSplitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        contentSplitter.setResizeWeight(0.5);

        // Left panel (Request/Response)
        JPanel inputPanel = createInputPanel();
        contentSplitter.setLeftComponent(inputPanel);

        // Right panel (Results)
        JPanel resultPanel = createResultPanel();
        contentSplitter.setRightComponent(resultPanel);

        add(contentSplitter, BorderLayout.CENTER);

        // Bottom status panel
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Create the control panel (buttons and dropdowns)
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Provider selection
        panel.add(new JLabel("Provider:"));
        providerCombo = new JComboBox<>(new String[]{"OpenAI", "Claude"});
        providerCombo.setSelectedItem(settings.getSelectedProvider().equals("claude") ? "Claude" : "OpenAI");
        panel.add(providerCombo);

        panel.add(Box.createHorizontalStrut(20));

        // Analysis mode selection
        panel.add(new JLabel("Mode:"));
        modeCombo = new JComboBox<>(new String[]{"Passive", "Active", "Explain"});
        String mode = settings.getAnalysisMode();
        if ("active".equalsIgnoreCase(mode)) {
            modeCombo.setSelectedItem("Active");
        } else if ("explain".equalsIgnoreCase(mode)) {
            modeCombo.setSelectedItem("Explain");
        } else {
            modeCombo.setSelectedItem("Passive");
        }
        panel.add(modeCombo);

        panel.add(Box.createHorizontalStrut(20));

        // Analyze button
        analyzeButton = new JButton("🔍 Analyze with AI");
        analyzeButton.addActionListener(this::performAnalysis);
        panel.add(analyzeButton);

        // Settings button
        settingsButton = new JButton("⚙️ Settings");
        settingsButton.addActionListener(e -> showSettings());
        panel.add(settingsButton);

        // Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearAll());
        panel.add(clearButton);

        return panel;
    }

    /**
     * Create the input panel (request/response display)
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("HTTP Request/Response"));

        // Create a tabbed pane for request and response
        JTabbedPane tabbedPane = new JTabbedPane();

        // Request tab
        requestArea = new JTextArea();
        requestArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        requestArea.setLineWrap(true);
        requestArea.setWrapStyleWord(false);
        JScrollPane requestScroll = new JScrollPane(requestArea);
        tabbedPane.addTab("Request", requestScroll);

        // Response tab
        responseArea = new JTextArea();
        responseArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(false);
        responseArea.setText("Paste HTTP response here (optional but recommended for better analysis).\n\nExample:\nHTTP/1.1 200 OK\nContent-Type: application/json\n\n{\"status\":\"success\",\"data\":\"...\"}\n\nTo get the response:\n1. Run the request in Burp Repeater\n2. Copy the full response from the Response tab\n3. Paste it here\n4. Click 'Analyze with AI'");
        responseArea.setForeground(new java.awt.Color(150, 150, 150)); // Gray text for placeholder
        JScrollPane responseScroll = new JScrollPane(responseArea);
        tabbedPane.addTab("Response", responseScroll);

        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Create the result panel
     */
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Analysis Results"));

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(false);
        resultArea.setText("Select a request and click 'Analyze with AI' to see results here.");

        JScrollPane scroll = new JScrollPane(resultArea);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create the status panel
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Ready");
        panel.add(statusLabel);
        return panel;
    }

    /**
     * Perform AI analysis
     */
    private void performAnalysis(ActionEvent e) {
        if (currentWorker != null && !currentWorker.isDone()) {
            JOptionPane.showMessageDialog(this, "Analysis already in progress. Please wait.");
            return;
        }

        String request = requestArea.getText().trim();
        String response = responseArea.getText().trim();

        if (request.isEmpty() && response.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please paste a request and/or response to analyze.");
            return;
        }

        // Update settings based on UI selections
        String provider = ((String) providerCombo.getSelectedItem()).toLowerCase();
        String mode = ((String) modeCombo.getSelectedItem()).toLowerCase();

        settings.setSelectedProvider(provider);
        settings.setAnalysisMode(mode);

        // Create analysis request
        AnalysisRequest analysisRequest = new AnalysisRequest(
                request,
                response,
                mode,
                provider
        );

        // Get the appropriate provider
        AIProvider aiProvider;
        if ("claude".equalsIgnoreCase(provider)) {
            aiProvider = new ClaudeProvider(settings);
        } else {
            aiProvider = new OpenAIProvider(settings);
        }

        if (!aiProvider.isConfigured()) {
            JOptionPane.showMessageDialog(this,
                    "API key not configured for " + aiProvider.getProviderName() + ".\n" +
                    "Please click 'Settings' and enter your API key.");
            return;
        }

        // Disable buttons and update status
        analyzeButton.setEnabled(false);
        statusLabel.setText("Analyzing... (0%)");
        resultArea.setText("Analyzing your request/response...\n\nPlease wait for the AI to complete the analysis.");

        // Create and start async worker
        currentWorker = new AnalysisWorker(aiProvider, analysisRequest, () -> {
            try {
                AnalysisResult result = currentWorker.get();
                if (result.isSuccess()) {
                    resultArea.setText(result.toString());
                    statusLabel.setText("Analysis complete (" + result.getProcessingTimeMs() + "ms)");
                } else {
                    resultArea.setText("Error: " + result.getErrorMessage());
                    statusLabel.setText("Analysis failed");
                }
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
                statusLabel.setText("Analysis failed");
            } finally {
                analyzeButton.setEnabled(true);
            }
        });

        currentWorker.execute();
    }

    /**
     * Show the settings dialog
     */
    private void showSettings() {
        SettingsPanel settingsPanel = new SettingsPanel(settings);
        int option = JOptionPane.showConfirmDialog(
                this,
                settingsPanel,
                "BurpAI Pro Settings",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            settingsPanel.saveSettings();
            statusLabel.setText("Settings saved");
        }
    }

    /**
     * Clear all text areas
     */
    private void clearAll() {
        requestArea.setText("");
        responseArea.setText("");
        resultArea.setText("");
        statusLabel.setText("Cleared");
    }

    /**
     * Load request and response into the UI
     */
    public void setRequestResponse(IHttpRequestResponse messageInfo) {
        if (messageInfo.getRequest() != null) {
            byte[] requestBytes = messageInfo.getRequest();
            requestArea.setText(new String(requestBytes));
        }

        if (messageInfo.getResponse() != null) {
            byte[] responseBytes = messageInfo.getResponse();
            responseArea.setText(new String(responseBytes));
        }

        resultArea.setText("");
        statusLabel.setText("Request/Response loaded. Click 'Analyze with AI' to begin.");
    }
}

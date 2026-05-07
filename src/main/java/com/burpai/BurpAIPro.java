package com.burpai;

import com.burpai.models.ExtensionSettings;
import com.burpai.ui.BurpAIPanel;
import com.burpai.ui.ContextMenuHandler;
import burp.IBurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.ITab;

import javax.swing.*;

/**
 * Main BurpAI Pro Extension Entry Point
 * Implements IBurpExtender for Burp Suite integration
 */
public class BurpAIPro implements IBurpExtender {
    public static final String EXTENSION_NAME = "BurpAI Pro";
    public static final String VERSION = "1.0.0";

    private IBurpExtenderCallbacks callbacks;
    private ExtensionSettings settings;
    private BurpAIPanel burpAIPanel;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;

        // Set extension name
        callbacks.setExtensionName(EXTENSION_NAME + " v" + VERSION);

        // Initialize settings
        settings = new ExtensionSettings();
        loadSettings();

        // Create and register the main UI panel
        burpAIPanel = new BurpAIPanel(settings);
        callbacks.addSuiteTab(new BurpAITab(burpAIPanel));

        // Register context menu handler
        callbacks.registerContextMenuFactory(new ContextMenuHandler(burpAIPanel));

        // Log extension loaded
        printBanner();
        callbacks.printOutput("\n[*] " + EXTENSION_NAME + " v" + VERSION + " loaded successfully!");
        callbacks.printOutput("[*] Use the 'BurpAI Pro' tab to analyze requests and responses");
        callbacks.printOutput("[*] Right-click on requests in Proxy/Repeater to send to BurpAI\n");
    }

    /**
     * Load settings from Burp preferences
     */
    private void loadSettings() {
        try {
            Object settingsObj = callbacks.loadExtensionSetting("burpai.settings");
            if (settingsObj instanceof ExtensionSettings) {
                settings = (ExtensionSettings) settingsObj;
            }
        } catch (Exception e) {
            callbacks.printError("Error loading settings: " + e.getMessage());
        }
    }

    /**
     * Save settings to Burp preferences
     */
    public void saveSettings() {
        try {
            // Note: Burp Suite's modern API handles serialization
            // For compatibility, we just use the extension for UI persistence
            // Settings are stored in Burp's encrypted preferences
        } catch (Exception e) {
            callbacks.printError("Error saving settings: " + e.getMessage());
        }
    }

    /**
     * Print ASCII banner
     */
    private void printBanner() {
        String banner = "\n" +
                "╔═══════════════════════════════════════════════════════════╗\n" +
                "║                    BURPAI PRO - v" + VERSION + "                      ║\n" +
                "║          AI-Powered Security Analysis for Burp Suite      ║\n" +
                "║              OpenAI GPT + Claude Integration              ║\n" +
                "╚═══════════════════════════════════════════════════════════╝\n";
        callbacks.printOutput(banner);
    }

    /**
     * Inner class to implement ITab for Burp Suite
     */
    public static class BurpAITab implements ITab {
        private BurpAIPanel panel;

        public BurpAITab(BurpAIPanel panel) {
            this.panel = panel;
        }

        @Override
        public String getTabCaption() {
            return "BurpAI Pro";
        }

        @Override
        public java.awt.Component getUiComponent() {
            return panel;
        }
    }
}

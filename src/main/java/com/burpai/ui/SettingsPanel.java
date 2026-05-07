package com.burpai.ui;

import com.burpai.models.ExtensionSettings;

import javax.swing.*;
import java.awt.*;

/**
 * Settings panel for configuring BurpAI Pro
 */
public class SettingsPanel extends JPanel {
    private ExtensionSettings settings;
    private JPasswordField openAiKeyField;
    private JPasswordField claudeKeyField;
    private JComboBox<String> openAiModelCombo;
    private JComboBox<String> claudeModelCombo;
    private JSlider temperatureSlider;
    private JSpinner maxTokensSpinner;
    private JCheckBox maskDataCheckbox;
    private JCheckBox autoAnalyzeCheckbox;

    public SettingsPanel(ExtensionSettings settings) {
        this.settings = settings;
        initializeUI();
    }

    /**
     * Initialize the settings UI
     */
    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(600, 500));

        // OpenAI Settings
        add(createSection("OpenAI Settings", createOpenAIPanel()));

        add(Box.createVerticalStrut(10));

        // Claude Settings
        add(createSection("Claude Settings", createClaudePanel()));

        add(Box.createVerticalStrut(10));

        // Common Settings
        add(createSection("Common Settings", createCommonPanel()));

        add(Box.createVerticalGlue());
    }

    /**
     * Create a titled section
     */
    private JPanel createSection(String title, JPanel content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Create OpenAI settings panel
     */
    private JPanel createOpenAIPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // API Key
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(new JLabel("API Key:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        openAiKeyField = new JPasswordField(settings.getOpenAiApiKey(), 30);
        panel.add(openAiKeyField, gbc);

        // Model selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Model:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        openAiModelCombo = new JComboBox<>(new String[]{
                "gpt-4", "gpt-4-turbo", "gpt-4o", "gpt-3.5-turbo"
        });
        openAiModelCombo.setSelectedItem(settings.getOpenAiModel());
        panel.add(openAiModelCombo, gbc);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        return panel;
    }

    /**
     * Create Claude settings panel
     */
    private JPanel createClaudePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // API Key
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(new JLabel("API Key:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        claudeKeyField = new JPasswordField(settings.getClaudeApiKey(), 30);
        panel.add(claudeKeyField, gbc);

        // Model selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Model:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        claudeModelCombo = new JComboBox<>(new String[]{
                "claude-haiku-4-5-20251001",
                "claude-opus-4-1-20250805",
                "claude-sonnet-4-20250514",
                "claude-3-sonnet-20240229",
                "claude-3-opus-20240229",
                "claude-3-haiku-20240307"
        });
        claudeModelCombo.setSelectedItem(settings.getClaudeModel());
        panel.add(claudeModelCombo, gbc);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        return panel;
    }

    /**
     * Create common settings panel
     */
    private JPanel createCommonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Temperature slider
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(new JLabel("Temperature (0-2):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        temperatureSlider = new JSlider(0, 200, (int) (settings.getTemperature() * 100));
        temperatureSlider.setMajorTickSpacing(50);
        temperatureSlider.setMinorTickSpacing(5);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        panel.add(temperatureSlider, gbc);

        // Max Tokens
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Max Tokens:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        maxTokensSpinner = new JSpinner(new SpinnerNumberModel(
                settings.getMaxTokens(), 100, 4000, 100
        ));
        panel.add(maxTokensSpinner, gbc);

        // Mask Sensitive Data
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        maskDataCheckbox = new JCheckBox("Mask sensitive data before analysis", settings.isMaskSensitiveData());
        panel.add(maskDataCheckbox, gbc);

        // Auto Analyze
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        autoAnalyzeCheckbox = new JCheckBox("Auto-analyze proxy traffic (if implemented)", settings.isAutoAnalyze());
        autoAnalyzeCheckbox.setEnabled(false); // Disabled for now
        panel.add(autoAnalyzeCheckbox, gbc);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        return panel;
    }

    /**
     * Save settings from UI to the settings object
     */
    public void saveSettings() {
        settings.setOpenAiApiKey(new String(openAiKeyField.getPassword()));
        settings.setClaudeApiKey(new String(claudeKeyField.getPassword()));

        settings.setOpenAiModel((String) openAiModelCombo.getSelectedItem());
        settings.setClaudeModel((String) claudeModelCombo.getSelectedItem());

        settings.setTemperature(temperatureSlider.getValue() / 100.0);
        settings.setMaxTokens((Integer) maxTokensSpinner.getValue());

        settings.setMaskSensitiveData(maskDataCheckbox.isSelected());
        settings.setAutoAnalyze(autoAnalyzeCheckbox.isSelected());
    }
}

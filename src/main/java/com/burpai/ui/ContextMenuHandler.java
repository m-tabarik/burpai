package com.burpai.ui;

import burp.IContextMenuInvocation;
import burp.IContextMenuFactory;
import burp.IHttpRequestResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Context menu handler for right-click "Send to BurpAI" option
 */
public class ContextMenuHandler implements IContextMenuFactory {
    private BurpAIPanel burpAIPanel;

    public ContextMenuHandler(BurpAIPanel burpAIPanel) {
        this.burpAIPanel = burpAIPanel;
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        List<JMenuItem> menuItems = new ArrayList<>();

        try {
            int context = invocation.getInvocationContext();

            // Only add menu item if we have selected messages
            IHttpRequestResponse[] selectedMessages = invocation.getSelectedMessages();
            if (selectedMessages == null || selectedMessages.length == 0) {
                return menuItems;
            }

            // Create menu item for any supported context
            JMenuItem sendToBurpAI = new JMenuItem("Send to BurpAI Pro");
            sendToBurpAI.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Load the first selected message into BurpAI
                    if (selectedMessages.length > 0) {
                        burpAIPanel.setRequestResponse(selectedMessages[0]);
                    }
                }
            });

            menuItems.add(sendToBurpAI);
        } catch (Exception e) {
            // Silently ignore if context is not available
        }

        return menuItems;
    }
}

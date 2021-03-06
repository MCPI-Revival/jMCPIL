package com.thebrokenrail.mcpil.gui.tab;

import javax.swing.JTabbedPane;

/**
 * Main Tabs
 */
public class MainTabs extends JTabbedPane {
    public MainTabs() {
        super();

        // Add Tabs
        addTab("Play", new PlayTab());
        addTab("Features", new FeaturesTab());
        addTab("Multiplayer", new MultiplayerTab());
        addTab("Settings", new SettingsTab());
        addTab("About", new AboutTab());
    }
}

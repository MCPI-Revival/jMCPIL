package com.thebrokenrail.jmcpil.gui.tab;

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
        addTab("Servers", new ServersTab());
        addTab("Settings", new SettingsTab());
        addTab("About", new AboutTab());
    }
}

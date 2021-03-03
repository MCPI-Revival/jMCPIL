package com.thebrokenrail.mcpil;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.thebrokenrail.mcpil.config.ConfigHandler;
import com.thebrokenrail.mcpil.gui.control.MainButtons;
import com.thebrokenrail.mcpil.gui.tab.MainTabs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Main {
    private static void run() {
        // Install Dark Theme
        FlatDarculaLaf.install();

        // Show GUI
        JFrame frame = new JFrame("MCPIL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(480, 360);
        frame.setMinimumSize(size);
        frame.setPreferredSize(size);

        // Create Main Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints layoutRestrictions;

        // Add Widgets
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 0;
        layoutRestrictions.weightx = 1;
        layoutRestrictions.weighty = 1;
        layoutRestrictions.fill = GridBagConstraints.BOTH;
        panel.add(new MainTabs(), layoutRestrictions);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 1;
        layoutRestrictions.weightx = 1;
        layoutRestrictions.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new MainButtons(frame.getRootPane()), layoutRestrictions);

        // Add Main Layout
        frame.add(panel, BorderLayout.CENTER);

        // Show UI
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Load Config
        ConfigHandler.load();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::run);
    }
}

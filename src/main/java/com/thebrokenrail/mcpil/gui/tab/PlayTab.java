package com.thebrokenrail.mcpil.gui.tab;

import com.thebrokenrail.mcpil.util.Profile;
import com.thebrokenrail.mcpil.util.Splash;
import com.thebrokenrail.mcpil.util.Util;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Play Tab
 */
public class PlayTab extends JPanel implements ActionListener {
    private final JComboBox<String> profile;

    PlayTab() {
        super(new GridBagLayout());
        GridBagConstraints layoutRestrictions;

        // Add Splash
        JLabel splash = new JLabel();
        splash.setText(Splash.get());
        splash.setForeground(Color.YELLOW);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 0;
        Util.addPadding(splash, 8, 0, 0, 0);
        add(splash, layoutRestrictions);

        // Add Changelog
        JTextPane changelog = new JTextPane();
        changelog.setEditable(false);
        changelog.setContentType("text/html");
        changelog.setText(Util.readFile("/opt/mcpil/CHANGELOG"));
        changelog.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(changelog);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 1;
        layoutRestrictions.weightx = 1;
        layoutRestrictions.weighty = 1;
        layoutRestrictions.fill = GridBagConstraints.BOTH;
        Util.addPadding(scroll, 8);
        add(scroll, layoutRestrictions);

        // Add Profile Selector
        profile = new JComboBox<>(Profile.PROFILES);
        profile.setEditable(false);
        profile.setSelectedItem(Profile.getCurrentProfile());
        profile.setActionCommand("select");
        profile.addActionListener(this);
        JPanel wrappedProfile = Util.wrap(profile);
        Util.addPadding(wrappedProfile, 8, 0);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 2;
        layoutRestrictions.weightx = 1;
        add(wrappedProfile, layoutRestrictions);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("select")) {
            Profile.setCurrentProfile((String) profile.getSelectedItem());
        }
    }
}

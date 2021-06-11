package com.thebrokenrail.jmcpil.gui.tab;

import com.thebrokenrail.jmcpil.util.Util;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * About Tab
 */
public class AboutTab extends JPanel {
    AboutTab() {
        super(new GridBagLayout());
        GridBagConstraints layoutRestrictions;

        // Add Title
        JLabel title = new JLabel();
        title.setText("Minecraft Pi Launcher");
        title.setFont(title.getFont().deriveFont(36f));
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 0;
        Util.addPadding(title, 6, 0, 6, 6);
        add(title, layoutRestrictions);

        // Get Version
        String versionStr = Util.readFile("/opt/jmcpil/VERSION");
        if (versionStr.length() > 0 && Character.isDigit(versionStr.charAt(0))) {
            versionStr = 'v' + versionStr;
        }
        // Add Version
        JLabel version = new JLabel();
        version.setText(versionStr);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 1;
        Util.addPadding(version, 6, 0, 6, 6);
        add(version, layoutRestrictions);

        // Add URL
        JLabel url = new JLabel();
        url.setText("https://github.com/MCPI-Revival/jMCPIL");
        url.setForeground(new Color(30, 144, 255));
        url.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        url.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    Desktop.getDesktop().browse(new URI(url.getText()));
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 2;
        Util.addPadding(url, 6, 6, 6, 6);
        add(url, layoutRestrictions);
    }
}

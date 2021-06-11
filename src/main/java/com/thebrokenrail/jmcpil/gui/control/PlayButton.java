package com.thebrokenrail.jmcpil.gui.control;

import com.thebrokenrail.jmcpil.config.Config;
import com.thebrokenrail.jmcpil.config.ConfigHandler;
import com.thebrokenrail.jmcpil.util.Launcher;
import com.thebrokenrail.jmcpil.util.Profile;
import com.thebrokenrail.jmcpil.util.Util;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Play Button
 */
public class PlayButton extends JButton implements ActionListener {
    private volatile Process process = null;

    PlayButton() {
        super("Play");

        setActionCommand("play");
        addActionListener(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Kill MCPI-Reborn
            if (process != null) {
                Util.kill(process.toHandle());
            }
        }));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("play") && process == null) {
            // Start MCPI-Reborn
            Config config = ConfigHandler.getConfig();
            process = Launcher.run(Profile.getFeatures(Profile.getCurrentProfile()), config.general.renderDistance, config.general.username);
            setEnabled(false);

            // Hide Launcher On MCPI Open
            if (config.general.hideLauncher) {
                SwingUtilities.getWindowAncestor(this).setVisible(false);
            }

            // Wait For Exit Thread
            new Thread(() -> {
                // Wait For Exit
                while (true) {
                    try {
                        process.waitFor();
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Re-Enable Button
                process = null;
                SwingUtilities.invokeLater(() -> {
                    setEnabled(true);
                    SwingUtilities.getWindowAncestor(PlayButton.this).setVisible(true);
                });
            }).start();
        }
    }
}

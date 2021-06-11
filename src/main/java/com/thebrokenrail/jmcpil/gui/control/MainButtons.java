package com.thebrokenrail.jmcpil.gui.control;

import com.thebrokenrail.jmcpil.config.ConfigHandler;
import com.thebrokenrail.jmcpil.util.Util;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Save/Play Buttons
 */
public class MainButtons extends JPanel implements ActionListener {
    public MainButtons(JRootPane root) {
        super(new FlowLayout(FlowLayout.RIGHT));

        // Add Buttons
        JButton save = new JButton("Save");
        save.setActionCommand("save");
        save.addActionListener(this);
        add(save);
        JButton play = new PlayButton();
        play.setDefaultCapable(true);
        root.setDefaultButton(play);
        add(play);

        // Add Padding
        Util.addPadding(this, 8);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("save")) {
            ConfigHandler.save();
        }
    }
}

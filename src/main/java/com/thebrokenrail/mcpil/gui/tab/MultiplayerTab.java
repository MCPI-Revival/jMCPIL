package com.thebrokenrail.mcpil.gui.tab;

import com.thebrokenrail.mcpil.config.Config;
import com.thebrokenrail.mcpil.proxy.GlobalProxy;
import com.thebrokenrail.mcpil.util.Util;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Multiplayer Tab
 */
public class MultiplayerTab extends BaseSettingsTab {
    MultiplayerTab() {
        super();

        List<Map.Entry<String, JComponent>> components = new ArrayList<>();

        // Add IP
        JTextField ip = new JTextField();
        components.add(Map.entry("IP:", Util.wrap(ip)));
        Config.addHandler("save", config -> config.multiplayer.ip = ip.getText());
        Config.addHandler("load", config -> ip.setText(config.multiplayer.ip));

        // Add Port
        JTextField port = new JTextField();
        port.setHorizontalAlignment(SwingConstants.LEADING);
        components.add(Map.entry("Port:", Util.wrap(port)));
        Config.addHandler("save", config -> {
            int portNum;
            try {
                portNum = Integer.parseInt(port.getText());
            } catch (NumberFormatException e) {
                portNum = 19132;
            }
            config.multiplayer.port = portNum;
        });
        Config.addHandler("load", config -> port.setText(String.valueOf(config.multiplayer.port)));

        // Run Proxy
        Config.addHandler("apply", config -> GlobalProxy.start(config.multiplayer.ip, config.multiplayer.port));

        addComponents(components, false);
    }
}

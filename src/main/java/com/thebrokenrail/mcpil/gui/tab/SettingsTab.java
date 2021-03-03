package com.thebrokenrail.mcpil.gui.tab;

import com.thebrokenrail.mcpil.config.Config;
import com.thebrokenrail.mcpil.util.RenderDistance;
import com.thebrokenrail.mcpil.util.Util;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Setting Tab
 */
public class SettingsTab extends BaseSettingsTab {
    SettingsTab() {
        super();

        List<Map.Entry<String, JComponent>> components = new ArrayList<>();

        // Add Render Distance
        JComboBox<RenderDistance> renderDistance = new JComboBox<>(RenderDistance.values());
        renderDistance.setEditable(false);
        components.add(Map.entry("Render Distance:", Util.wrap(renderDistance)));
        Config.addHandler("save", config -> config.general.renderDistance = (RenderDistance) renderDistance.getSelectedItem());
        Config.addHandler("load", config -> renderDistance.setSelectedItem(config.general.renderDistance));

        // Add Username
        JTextField username = new JTextField();
        components.add(Map.entry("Username:", Util.wrap(username)));
        Config.addHandler("save", config -> config.general.username = username.getText());
        Config.addHandler("load", config -> username.setText(config.general.username));

        // Add Hide Launcher
        JCheckBox hideLauncher = new JCheckBox();
        components.add(Map.entry("Hide Launcher:", hideLauncher));
        Config.addHandler("save", config -> config.general.hideLauncher = hideLauncher.isSelected());
        Config.addHandler("load", config -> hideLauncher.setSelected(config.general.hideLauncher));

        addComponents(components, false);
    }
}

package com.thebrokenrail.jmcpil.gui.tab;

import com.thebrokenrail.jmcpil.config.Config;
import com.thebrokenrail.jmcpil.util.Launcher;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Features Tab
 */
public class FeaturesTab extends JScrollPane {
    FeaturesTab() {
        super(new InnerFeaturesTab());

        setBorder(null);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * Inner Features Tab
     */
    private static class InnerFeaturesTab extends BaseSettingsTab {
        InnerFeaturesTab() {
            super();

            List<Map.Entry<String, JComponent>> components = new ArrayList<>();

            // Add Features
            for (String feature : Launcher.AVAILABLE_FEATURES.keySet()) {
                // Create Widget
                JCheckBox component = new JCheckBox();
                components.add(Map.entry(feature, component));
                Config.addHandler("save", config -> {
                    if (component.isSelected()) {
                        if (!config.general.customFeatures.contains(feature)) {
                            config.general.customFeatures.add(feature);
                        }
                    } else {
                        config.general.customFeatures.remove(feature);
                    }
                });
                Config.addHandler("load", config -> component.setSelected(config.general.customFeatures.contains(feature)));
            }

            addComponents(components, true);
        }
    }
}

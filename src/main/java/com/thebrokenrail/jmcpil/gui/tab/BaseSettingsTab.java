package com.thebrokenrail.jmcpil.gui.tab;

import com.thebrokenrail.jmcpil.util.Util;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Map;

/**
 * Parent Class OF All Settings Tabs
 */
public class BaseSettingsTab extends JPanel {
    BaseSettingsTab() {
        super(new GridBagLayout());
    }

    /**
     * Add Components
     * @param map Components
     * @param putLabelLast Whether To Place Label After The Component
     */
    protected void addComponents(List<Map.Entry<String, JComponent>> map, boolean putLabelLast) {
        // Add Component
        GridBagConstraints layoutRestrictions;
        int row = 0;
        for (Map.Entry<String, JComponent> entry : map) {
            // Prepare
            int currentRow = row++;

            // Create Label
            JLabel label = new JLabel();
            label.setText(entry.getKey());
            layoutRestrictions = new GridBagConstraints();
            layoutRestrictions.gridx = putLabelLast ? 1 : 0;
            layoutRestrictions.gridy = currentRow;
            layoutRestrictions.weightx = putLabelLast ? 1 : 0;
            layoutRestrictions.fill = GridBagConstraints.HORIZONTAL;
            Util.addPadding(label, currentRow == 0 ? 8 : 0, 8, 8, 0);
            add(label, layoutRestrictions);

            // Add Component
            layoutRestrictions = new GridBagConstraints();
            layoutRestrictions.gridx = putLabelLast ? 0 : 1;
            layoutRestrictions.gridy = currentRow;
            layoutRestrictions.weightx = putLabelLast ? 0 : 1;
            layoutRestrictions.fill = GridBagConstraints.HORIZONTAL;
            Util.addPadding(entry.getValue(), currentRow == 0 ? 8 : 0, 8, 8, 8);
            add(entry.getValue(), layoutRestrictions);
        }
        // Add Glue
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = row;
        layoutRestrictions.gridwidth = 2;
        layoutRestrictions.weighty = 1;
        add(Box.createVerticalGlue(), layoutRestrictions);
    }
}

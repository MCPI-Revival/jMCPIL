package com.thebrokenrail.jmcpil.gui.tab;

import com.thebrokenrail.jmcpil.config.Config;
import com.thebrokenrail.jmcpil.util.Util;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servers Tab
 */
public class ServersTab extends JPanel implements ActionListener {
    /**
     * Server List Widget
     */
    private final JList<String> list;
    /**
     * New Server Text Entry Widget
     */
    private final JTextField entry;

    ServersTab() {
        super(new GridBagLayout());

        // Create List
        list = new JList<>(new DefaultListModel<>());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        // Configure Config
        Config.addHandler("save", config -> {
            config.servers.clear();
            for (int i = 0; i < list.getModel().getSize(); i++) {
                config.servers.add(list.getModel().getElementAt(i));
            }
        });
        Config.addHandler("load", config -> {
            // Sanitize
            List<String> newServers = new ArrayList<>();
            for (String server : config.servers) {
                if (server.length() > 0) {
                    newServers.add(server.replaceAll("\n", " ").replaceAll("\r", ""));
                }
            }
            config.servers = newServers;
            // Add
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addAll(config.servers);
            list.setModel(model);
        });
        Config.addHandler("apply", config -> {
            try {
                File serverList = new File(System.getenv("HOME"), ".minecraft-pi/servers.txt");
                //noinspection ResultOfMethodCallIgnored
                serverList.getParentFile().mkdirs();
                //noinspection ResultOfMethodCallIgnored
                serverList.createNewFile();
                FileWriter writer = new FileWriter(serverList, false);
                writer.write("# Managed By jMCPIL\n");
                for (String server : config.servers) {
                    writer.write(server + '\n');
                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Add List
        JScrollPane scrollingList = new JScrollPane(list);
        scrollingList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel wrappedList = Util.wrap(scrollingList);
        GridBagConstraints layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 0;
        layoutRestrictions.gridwidth = 4;
        layoutRestrictions.weightx = 1;
        layoutRestrictions.weighty = 1;
        layoutRestrictions.fill = GridBagConstraints.BOTH;
        Util.addPadding(wrappedList, 8, 0, 8, 8);
        add(wrappedList, layoutRestrictions);

        // Add Delete Button
        JButton delete = new JButton("Delete");
        delete.setActionCommand("delete");
        delete.addActionListener(this);
        // Wrap
        JPanel wrappedDelete = Util.wrap(delete);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 0;
        layoutRestrictions.gridy = 1;
        Util.addPadding(wrappedDelete, 8);
        add(wrappedDelete, layoutRestrictions);

        // Add Separator
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        // Wrap
        JPanel wrappedSeparator = Util.wrap(separator);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 1;
        layoutRestrictions.gridy = 1;
        layoutRestrictions.fill = GridBagConstraints.VERTICAL;
        Util.addPadding(wrappedSeparator, 0, 8);
        add(wrappedSeparator, layoutRestrictions);

        // Add New Server Entry
        entry = new JTextField();
        entry.setActionCommand("add");
        entry.addActionListener(this);
        // Wrap
        JPanel wrappedEntry = Util.wrap(entry);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 2;
        layoutRestrictions.gridy = 1;
        layoutRestrictions.weightx = 1;
        layoutRestrictions.fill = GridBagConstraints.HORIZONTAL;
        Util.addPadding(wrappedEntry, 8);
        add(wrappedEntry, layoutRestrictions);

        // Add New Server Button
        JButton add = new JButton("Add");
        add.setActionCommand("add");
        add.addActionListener(this);
        // Wrap
        JPanel wrappedAdd = Util.wrap(add);
        layoutRestrictions = new GridBagConstraints();
        layoutRestrictions.gridx = 3;
        layoutRestrictions.gridy = 1;
        Util.addPadding(wrappedAdd, 8, 8, 0, 8);
        add(wrappedAdd, layoutRestrictions);
    }

    /**
     * Handle Button Press
     * @param actionEvent Action To Handle
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("delete")) {
            // Delete Selected Server
            int index = list.getSelectedIndex();
            if (index != -1) {
                ((DefaultListModel<String>) list.getModel()).remove(index);
            }
        } else if (action.equals("add")) {
            // Add New Server
            String newServer = entry.getText();
            entry.setText("");
            if (newServer.length() > 0) {
                ((DefaultListModel<String>) list.getModel()).addElement(newServer);
            }
        }
    }
}

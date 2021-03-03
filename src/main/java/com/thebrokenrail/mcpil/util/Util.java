package com.thebrokenrail.mcpil.util;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Utility Functions
 */
public class Util {
    /**
     * Add Padding
     * @param component Component
     * @param top Padding To Add To Top
     * @param bottom Padding To Add To Bottom
     * @param left Padding To Add To Left
     * @param right Padding To Add To Right
     */
    public static void addPadding(JComponent component, int top, int bottom, int left, int right) {
        component.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(top, left, bottom, right), component.getBorder()));
    }
    /**
     * Add Padding
     * @param component Component
     * @param x Padding To Add To Left/Right
     * @param y Padding To Add To Top/Bottom
     */
    public static void addPadding(JComponent component, int x, int y) {
        addPadding(component, y, y, x, x);
    }
    /**
     * Add Padding
     * @param component Component
     * @param value Padding To Add To All Sides
     */
    public static void addPadding(JComponent component, int value) {
        addPadding(component, value, value);
    }

    /**
     * Wrap Component In Panel
     * @param component Component
     * @return Component Wrapped In Panel
     */
    public static JPanel wrap(JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Kill Process
     * @param process Process
     */
    public static void kill(ProcessHandle process) {
        Collection<ProcessHandle> children = process.children().collect(Collectors.toList());
        process.destroyForcibly();
        children.forEach(Util::kill);
    }

    /**
     * Read File
     * @param fileName File Name
     * @return File Contents
     */
    public static String readFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName)).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: Unable To Read: " + fileName;
        }
    }
}

package com.thebrokenrail.mcpil.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility Class For Running MCPI-Reborn
 */
public class Launcher {
    private static Map<String, Boolean> getFeatures() {
        Map<String, Boolean> out = new HashMap<>();

        // Block X11 If Using Older MCPi-Docker
        Map<String, String> env = new HashMap<>(System.getenv());
        env.remove("DISPLAY");

        // Convert Environment To List<String
        List<String> emvList = new ArrayList<>();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            emvList.add(entry.getKey() + '=' + entry.getValue());
        }

        String result;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/usr/bin/minecraft-pi", "--print-features"}, emvList.toArray(new String[0]));

            StringBuilder builder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                int c;
                while ((c = reader.read()) != -1) {
                    builder.append((char) c);
                }
            }
            result = builder.toString();

            int exit = process.waitFor();
            if (exit != 0) {
                throw new UnsupportedOperationException();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        int stage = 0;
        int skip = 0;
        boolean escaped = false;
        boolean currentDefault = false;
        String currentName = "";
        for (char part : result.toCharArray()) {
            if (skip > 0) {
                skip--;
                continue;
            }
            if (stage == 0) {
                if (part == 'T') {
                    currentDefault = true;
                    skip = 3;
                    stage++;
                } else if (part == 'F') {
                    currentDefault = false;
                    skip = 4;
                    stage++;
                } else if (part != ' ' && part != '\n') {
                    throw new UnsupportedOperationException();
                }
            } else if (stage == 1) {
                if (part == '\'') {
                    stage++;
                }
            } else if (stage == 2) {
                boolean isEscaped = false;
                if (part == '\\') {
                    escaped = true;
                } else if (escaped) {
                    isEscaped = true;
                    escaped = false;
                    if (part == 'n') {
                        // Hide Newline
                        part = ' ';
                    } else if (part == 't') {
                        // Add Tab
                        part = '\t';
                    }
                }
                if (part == '\'' && !isEscaped) {
                    out.put(currentName, currentDefault);
                    currentName = "";
                    currentDefault = false;
                    stage = 0;
                } else {
                    currentName += part;
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        return Collections.unmodifiableMap(out);
    }

    /**
     * All Available Feature Flags
     */
    public static final Map<String, Boolean> AVAILABLE_FEATURES = getFeatures();

    /**
     * Run MCPI-Reborn
     * @param features Enabled Feature Flags
     * @param renderDistance Render Distance
     * @param username Username
     * @return MCPI-Reborn Process
     */
    public static Process run(List<String> features, RenderDistance renderDistance, String username) {
        // Prepare
        ProcessBuilder builder = new ProcessBuilder("/usr/bin/minecraft-pi");
        builder.inheritIO();
        // Prepare Environment
        Map<String, String> env = builder.environment();
        env.put("MCPI_FEATURES", String.join("|", features));
        env.put("MCPI_RENDER_DISTANCE", renderDistance.name());
        env.put("MCPI_USERNAME", username);
        builder.environment();
        // Run
        try {
            return builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert Feature Flag Map To Enabled Feature Flag List
     * @param map Feature Flag Map
     * @return Enabled Feature Flag List
     */
    public static List<String> flagMapToFlagList(Map<String, Boolean> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) {
                list.add(entry.getKey());
            }
        }
        return list;
    }
}

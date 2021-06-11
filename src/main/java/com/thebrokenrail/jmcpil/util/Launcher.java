package com.thebrokenrail.jmcpil.util;

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

        // Run
        String result;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"minecraft-pi-reborn-client", "--print-available-feature-flags"}, emvList.toArray(new String[0]));

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

        // Read Flags
        for (String line : result.split("\n")) {
            if (line.length() > 0) {
                if (line.startsWith("TRUE ")) {
                    out.put(line.substring(5), true);
                } else if (line.startsWith("FALSE ")) {
                    out.put(line.substring(6), false);
                } else {
                    throw new UnsupportedOperationException();
                }
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
        ProcessBuilder builder = new ProcessBuilder("minecraft-pi-reborn-client");
        builder.inheritIO();
        // Prepare Environment
        Map<String, String> env = builder.environment();
        env.put("MCPI_FEATURE_FLAGS", String.join("|", features));
        env.put("MCPI_RENDER_DISTANCE", renderDistance.name());
        env.put("MCPI_USERNAME", username);
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

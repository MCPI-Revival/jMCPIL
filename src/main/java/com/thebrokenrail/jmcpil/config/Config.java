package com.thebrokenrail.jmcpil.config;

import com.thebrokenrail.jmcpil.util.RenderDistance;
import com.thebrokenrail.jmcpil.util.Launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Config Object
 */
public class Config {
    private static final Map<String, List<Consumer<Config>>> handlers = new HashMap<>();
    /**
     * Add Config Event Handler
     * @param event Event Name (ie. save, load, apply)
     * @param handler Event Handler
     */
    public static void addHandler(String event, Consumer<Config> handler) {
        if (!handlers.containsKey(event)) {
            handlers.put(event, new ArrayList<>());
        }
        handlers.get(event).add(handler);
    }
    /**
     * Handle Config Event
     * @param event Event Name (ie. save, load, apply)
     * @param config Config Object
     */
    public static void handle(String event, Config config) {
        List<Consumer<Config>> eventHandlers = handlers.get(event);
        if (eventHandlers != null) {
            for (Consumer<Config> handler : eventHandlers) {
                handler.accept(config);
            }
        }
    }

    /**
     * General Settings
     */
    public static class GeneralSettings {
        /**
         * Username
         */
        public String username = "StevePi";
        /**
         * Render Distance
         */
        public RenderDistance renderDistance = RenderDistance.Short;
        /**
         * Features To Enable In Custom Profile
         */
        public List<String> customFeatures = Launcher.flagMapToFlagList(Launcher.AVAILABLE_FEATURES);
        /**
         * Hide Launcher On MCPI Open
         */
        public boolean hideLauncher = true;
    }
    /**
     * General Settings Property
     */
    public GeneralSettings general = new GeneralSettings();

    /**
     * Server List Property
     */
    public List<String> servers = new ArrayList<>(Collections.singletonList("thebrokenrail.com"));
}

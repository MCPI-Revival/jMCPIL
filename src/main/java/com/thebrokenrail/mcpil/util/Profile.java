package com.thebrokenrail.mcpil.util;

import com.thebrokenrail.mcpil.config.ConfigHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {
    /**
     * All Available Profiles
     */
    public static final String[] PROFILES = new String[]{
            "Classic MCPI",
            "Modded MCPI",
            "Modded MCPE",
            "Optimized MCPE",
            "Custom"
    };

    public static String currentProfile = PROFILES[2];
    /**
     * Set Current Profile
     * @param currentProfile New Current Profile
     */
    public static void setCurrentProfile(String currentProfile) {
        Profile.currentProfile = currentProfile;
    }
    /**
     * Get Current Profile
     * @return Current Profile
     */
    public static String getCurrentProfile() {
        return currentProfile;
    }

    /**
     * Get Feature Flags For Profile
     * @param profile Profile Name
     * @return Enabled Feature Flags
     */
    public static List<String> getFeatures(String profile) {
        int profileID = Arrays.asList(PROFILES).indexOf(profile);
        if (profileID == 0) {
            // No Features
            return Collections.emptyList();
        } else if (profileID == 1) {
            // Default Features Minus Touch GUI
            Map<String, Boolean> out = new HashMap<>(Launcher.AVAILABLE_FEATURES);
            out.put("Touch GUI", false);
            return Launcher.flagMapToFlagList(out);
        } else if (profileID == 2) {
            // Default Features
            return Launcher.flagMapToFlagList(Launcher.AVAILABLE_FEATURES);
        } else if (profileID == 3) {
            // Default Features With Lower Quality Graphics
            Map<String, Boolean> out = new HashMap<>(Launcher.AVAILABLE_FEATURES);
            out.put("Fancy Graphics", false);
            out.put("Smooth Lighting", false);
            out.put("Animated Water", false);
            out.put("Disable gui_blocks Atlas", false);
            return Launcher.flagMapToFlagList(out);
        } else if (profileID == 4) {
            // Custom Features (Use Features Tab)
            return ConfigHandler.getConfig().general.customFeatures;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}

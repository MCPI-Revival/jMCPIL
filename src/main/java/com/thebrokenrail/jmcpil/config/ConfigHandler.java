package com.thebrokenrail.jmcpil.config;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Config Handler (Loading/Saving)
 */
public class ConfigHandler {
    private ConfigHandler() {
    }

    private static Config config = new Config();
    /**
     * Get Config
     * @return Config
     */
    public static Config getConfig() {
        return config;
    }

    private static final String CONFIG_FILE = System.getenv("HOME") + "/.mcpil.json";

    /**
     * Save Config
     */
    public static void save() {
        Config.handle("save", config);
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(CONFIG_FILE);
            gson.toJson(config, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Config.handle("apply", config);
    }

    /**
     * Load Config
     */
    public static void load() {
        if (new File(CONFIG_FILE).exists()) {
            try {
                Gson gson = new Gson();
                FileReader reader = new FileReader(CONFIG_FILE);
                config = gson.fromJson(reader, Config.class);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            config = new Config();
        }
        Config.handle("load", config);
        Config.handle("apply", config);
    }
}

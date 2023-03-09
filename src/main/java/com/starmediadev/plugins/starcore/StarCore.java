package com.starmediadev.plugins.starcore;

import com.starmediadev.plugins.starcore.integration.StarIntegration;
import com.starmediadev.plugins.starcore.integration.starsql.StarSQLIntegration;
import com.starmediadev.plugins.starcore.scheduler.SpigotScheduler;
import com.starmediadev.starlib.StarLib;
import com.starmediadev.starlib.reflection.URLClassLoaderAccess;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URLClassLoader;
import java.util.*;
import java.util.logging.Level;

public class StarCore extends JavaPlugin {
    
    private Map<String, StarIntegration> integrations = new HashMap<>();
    
    public void onLoad() {
        StarLib.setScheduler(new SpigotScheduler(this));
        StarLib.setStarlibLogger(getLogger());
        createDefaultIntegrations();
    }
    
    public void onEnable() {
        saveDefaultConfig();
        loadLibraries();
        setupIntegrations();
        getServer().getScheduler().runTaskLater(this, () -> integrations.values().forEach(StarIntegration::callLoadEvents), 1L);
    }
    
    private void setupIntegrations() {
        for (StarIntegration integration : integrations.values()) {
            try {
                Class<?> integrationClass = Class.forName(integration.getClassName());
                getServer().getPluginManager().registerEvents(integration, this);
                integration.load();
                getLogger().info("Integration " + integration.getName() + " has be loaded successfully.");
            } catch (ClassNotFoundException e) {
                getLogger().fine("Could not find class for integration " + integration.getName());
            } catch (Exception e) {
                getLogger().log(Level.SEVERE, "Integration " + integration.getName() + " encountered an error while loading", e);
            }
        }
    }
    
    private void loadLibraries() {
        File librariesFolder = new File(getDataFolder(), "libraries");
        if (!librariesFolder.exists()) {
            librariesFolder.mkdirs();
        }
    
        URLClassLoaderAccess urlCLAccess = URLClassLoaderAccess.create((URLClassLoader) getClassLoader());
        
        try {
            for (File file : librariesFolder.listFiles()) {
                String name = file.getName();
                String extension = name.substring(name.lastIndexOf("."));
                if (extension.equalsIgnoreCase(".jar")) {
                    urlCLAccess.addURL(file.toURI().toURL());
                    getLogger().info("Added " + file.getName() + " as a library.");
                }
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Error while loading libraries", e);
        }
    }
    
    private void createDefaultIntegrations() {
        this.integrations.put("starsql", new StarSQLIntegration(this));
    }
}
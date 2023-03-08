package com.starmediadev.plugins.starcore;

import com.starmediadev.plugins.starcore.scheduler.SpigotScheduler;
import com.starmediadev.starlib.StarLib;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.*;
import java.util.logging.Level;

public class StarCore extends JavaPlugin {
    public void onLoad() {
        StarLib.setScheduler(new SpigotScheduler(this));
        StarLib.setStarlibLogger(getLogger());
    }
    
    public void onEnable() {
        saveDefaultConfig();
        loadLibraries();
    }
    
    private void loadLibraries() {
        File librariesFolder = new File(getDataFolder(), "libraries");
        if (!librariesFolder.exists()) {
            librariesFolder.mkdirs();
        }
        
        try {
            URLClassLoader urlClassLoader = (URLClassLoader) getClassLoader();
            Method addUrl = urlClassLoader.getClass().getDeclaredMethod("addURL", URL.class);
            addUrl.setAccessible(true);
            for (File file : librariesFolder.listFiles()) {
                String name = file.getName();
                String extension = name.substring(name.lastIndexOf("."));
                if (extension.equalsIgnoreCase(".jar")) {
                    addUrl.invoke(urlClassLoader, file.toURI().toURL());
                    getLogger().info("Added " + file.getName() + " as a library.");
                }
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Error while loading libraries", e);
        }
    }
}
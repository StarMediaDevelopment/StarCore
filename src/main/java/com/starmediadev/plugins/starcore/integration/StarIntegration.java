package com.starmediadev.plugins.starcore.integration;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class StarIntegration implements Listener {
    
    protected JavaPlugin plugin;
    protected String name;
    protected String className;
    
    public StarIntegration(JavaPlugin plugin, String name, String className) {
        this.plugin = plugin;
        this.name = name;
        this.className = className;
    }
    
    public abstract void load();
    public abstract void unload();
    public void callLoadEvents() {
        
    }
    
    public String getName() {
        return name;
    }
    
    public String getClassName() {
        return className;
    }
    
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
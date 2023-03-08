package com.starmediadev.plugins.starcore.scheduler;

import com.starmediadev.starlib.scheduler.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SpigotScheduler implements Scheduler {
    
    private final BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
    
    private JavaPlugin plugin;
    
    public SpigotScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public Task runTask(Runnable runnable) throws IllegalArgumentException {
        return new SpigotTask(bukkitScheduler.runTask(plugin, runnable));
    }
    
    @Override
    public Task runTaskAsynchronously(Runnable runnable) throws IllegalArgumentException {
        return new SpigotTask(bukkitScheduler.runTaskAsynchronously(plugin, runnable));
    }
    
    @Override
    public Task runTaskLater(Runnable runnable, long delay) throws IllegalArgumentException {
        return new SpigotTask(bukkitScheduler.runTaskLater(plugin, runnable, delay));
    }
    
    @Override
    public Task runTaskLaterAsynchronously(Runnable runnable, long delay) throws IllegalArgumentException {
        return new SpigotTask(bukkitScheduler.runTaskLaterAsynchronously(plugin, runnable, delay));
    }
    
    @Override
    public Task runTaskTimer(Runnable runnable, long delay, long period) throws IllegalArgumentException {
        return new SpigotTask(bukkitScheduler.runTaskTimer(plugin, runnable, delay, period));
    }
    
    @Override
    public Task runTaskTimerAsynchronously(Runnable runnable, long delay, long period) throws IllegalArgumentException {
        return new SpigotTask(bukkitScheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period));
    }
}

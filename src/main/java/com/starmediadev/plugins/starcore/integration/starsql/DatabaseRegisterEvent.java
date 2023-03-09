package com.starmediadev.plugins.starcore.integration.starsql;

import com.starmediadev.starsql.DatabaseRegistry;
import org.bukkit.event.*;

public class DatabaseRegisterEvent extends Event {
    
    private static HandlerList handlerList = new HandlerList();
    
    private DatabaseRegistry registry;
    
    public DatabaseRegisterEvent(DatabaseRegistry registry) {
        this.registry = registry;
    }
    
    public DatabaseRegistry getRegistry() {
        return registry;
    }
    
    public static HandlerList getHandlerList() {
        return handlerList;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}

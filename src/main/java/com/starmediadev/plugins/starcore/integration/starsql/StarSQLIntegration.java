package com.starmediadev.plugins.starcore.integration.starsql;

import com.starmediadev.plugins.starcore.integration.StarIntegration;
import com.starmediadev.starsql.DatabaseRegistry;
import com.starmediadev.starsql.objects.Database;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public class StarSQLIntegration extends StarIntegration {
    
    private DatabaseRegistry databaseRegistry;
    
    public StarSQLIntegration(JavaPlugin plugin) {
        super(plugin, "starsql", "com.starmediadev.starsql.StarSQL");
    }
    
    @Override
    public void load() {
        databaseRegistry = new DatabaseRegistry(plugin.getLogger());
        plugin.getServer().getServicesManager().register(DatabaseRegistry.class, databaseRegistry, plugin, ServicePriority.Normal);
    }
    
    @Override
    public void callLoadEvents() {
        plugin.getServer().getPluginManager().callEvent(new DatabaseRegisterEvent(databaseRegistry));
        try {
            databaseRegistry.setup();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "[StarSQL] There was an error while setting up the databases", e);
        }
    }
    
    @Override
    public void unload() {
        for (Database database : databaseRegistry.getObjects()) {
            database.flush();
        }
    }
}

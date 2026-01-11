package com.ricehead.tabblist;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

/**
 * Rice's Tabby List - Because apparently we needed ANOTHER tab list plugin
 * At least this one has a cool name, I guess
 */
public class RiceTabbyList extends JavaPlugin {
    
    private static RiceTabbyList instance;
    private ConfigManager configManager;
    private TPSTracker tpsTracker;
    private RamTracker ramTracker;
    private TabListManager tabListManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config (wow, groundbreaking stuff here)
        saveDefaultConfig();
        
        // Initialize managers (because who doesn't love managers?)
        configManager = new ConfigManager(this);
        tpsTracker = new TPSTracker(this);
        ramTracker = new RamTracker(this);
        tabListManager = new TabListManager(this);
        
        // Check for PlaceholderAPI (for AFK and other external placeholders)
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found! External placeholders will work.");
        } else {
            getLogger().warning("PlaceholderAPI not found! External placeholders won't work.");
        }
        
        // Register commands (ricehead because why the hell not)
        getCommand("ricehead").setExecutor(new RiceHeadCommand(this));
        
        // Start TPS tracking (let's watch this server burn in real-time)
        tpsTracker.startTracking();
        
        // Start updating tab lists (the whole point of this damn plugin)
        tabListManager.startUpdating();
        
        getLogger().info("Rice's Tabby List has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Clean up our mess before we leave
        if (tpsTracker != null) {
            tpsTracker.stopTracking();
        }
        if (tabListManager != null) {
            tabListManager.stopUpdating();
        }
        getLogger().info("Rice's Tabby List has been disabled!");
    }
    
    // Singleton pattern because we're fancy like that
    public static RiceTabbyList getInstance() {
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public TPSTracker getTPSTracker() {
        return tpsTracker;
    }
    
    public RamTracker getRamTracker() {
        return ramTracker;
    }
    
    public TabListManager getTabListManager() {
        return tabListManager;
    }
    
    /**
     * Reload the config because someone inevitably screwed it up
     */
    public void reloadPluginConfig() {
        reloadConfig();
        configManager = new ConfigManager(this);
        tabListManager.reload();
        getLogger().info("Configuration reloaded!");
    }
}

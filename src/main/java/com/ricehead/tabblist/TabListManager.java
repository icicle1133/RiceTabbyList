package com.ricehead.tabblist;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * Manages the tab list header, footer, and player names
 * Because manually updating tab lists is for peasants
 */
public class TabListManager {
    
    private final RiceTabbyList plugin;
    private BukkitTask updateTask;
    
    public TabListManager(RiceTabbyList plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Start updating tab lists for all players (the fun begins)
     */
    public void startUpdating() {
        int interval = plugin.getConfigManager().getUpdateInterval();
        
        updateTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            // Update tab list for all online players
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateTabList(player);
            }
        }, 20L, interval); // Start after 1 second, then repeat at configured interval
    }
    
    /**
     * Update the tab list for a specific player
     * @param player The lucky player getting their tab updated
     */
    public void updateTabList(Player player) {
        ConfigManager config = plugin.getConfigManager();
        
        // Build header (multiple lines joined with newlines)
        StringBuilder header = new StringBuilder();
        for (String line : config.getHeaderLines()) {
            if (header.length() > 0) header.append("\n");
            header.append(config.replacePlaceholders(line, player));
        }
        
        // Build footer (multiple lines joined with newlines)
        StringBuilder footer = new StringBuilder();
        for (String line : config.getFooterLines()) {
            if (footer.length() > 0) footer.append("\n");
            footer.append(config.replacePlaceholders(line, player));
        }
        
        // Set the header and footer (finally, the payoff)
        player.setPlayerListHeaderFooter(header.toString(), footer.toString());
        
        // Update player's display name in tab list
        String displayName = config.replacePlaceholders(config.getTabFormat(), player);
        player.setPlayerListName(displayName);
    }
    
    /**
     * Reload the tab list manager (when config changes)
     */
    public void reload() {
        // Restart the update task with new interval
        if (updateTask != null && !updateTask.isCancelled()) {
            updateTask.cancel();
        }
        startUpdating();
        
        // Force update all players immediately
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateTabList(player);
        }
    }
    
    /**
     * Stop updating when plugin disables (cleanup time)
     */
    public void stopUpdating() {
        if (updateTask != null && !updateTask.isCancelled()) {
            updateTask.cancel();
        }
    }
}

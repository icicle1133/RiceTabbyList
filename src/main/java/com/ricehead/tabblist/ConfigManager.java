package com.ricehead.tabblist;

import org.bukkit.configuration.file.FileConfiguration;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * Manages all the config bullshit so we don't have to deal with it elsewhere
 */
public class ConfigManager {
    
    private final RiceTabbyList plugin;
    private String domain;
    private List<String> headerLines;
    private List<String> footerLines;
    private String tabFormat;
    private int updateInterval;
    
    public ConfigManager(RiceTabbyList plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    
    /**
     * Load config values (hopefully the admin didn't fuck up the YAML)
     */
    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        
        // Get the domain (default to "yourserver.com" because nobody reads docs)
        domain = config.getString("domain", "yourserver.com");
        
        // Tab list header lines
        headerLines = config.getStringList("tab.header");
        
        // Tab list footer lines
        footerLines = config.getStringList("tab.footer");
        
        // Player name format in tab
        tabFormat = config.getString("tab.player-format", "%player%");
        
        // Update interval in ticks (default 20 = 1 second)
        updateInterval = config.getInt("tab.update-interval", 20);
    }
    
    /**
     * Get the server domain
     * @return The domain, or a placeholder if the admin is lazy
     */
    public String getDomain() {
        return domain;
    }
    
    public List<String> getHeaderLines() {
        return headerLines;
    }
    
    public List<String> getFooterLines() {
        return footerLines;
    }
    
    public String getTabFormat() {
        return tabFormat;
    }
    
    public int getUpdateInterval() {
        return updateInterval;
    }
    
    /**
     * Replace internal placeholders (the ones we actually support)
     * @param text Text to process
     * @param player Player for context (can be null)
     * @return Processed text
     */
    public String replacePlaceholders(String text, Player player) {
        if (text == null) return "";
        
        // Replace our internal placeholders
        text = text.replace("{tps}", plugin.getTPSTracker().getFormattedTPS());
        text = text.replace("{op_count}", String.valueOf(getOnlineOPCount()));
        text = text.replace("{players}", String.valueOf(Bukkit.getOnlinePlayers().size()));
        text = text.replace("{max_players}", String.valueOf(Bukkit.getMaxPlayers()));
        text = text.replace("{domain}", domain);
        
        // Player-specific placeholders
        if (player != null) {
            text = text.replace("{player}", player.getName());
            text = text.replace("{afk}", String.valueOf(isPlayerAFK(player)));
            text = text.replace("{afk_status}", isPlayerAFK(player) ? colorize("&7[AFK]") : "");
        }
        
        // Try to use PlaceholderAPI for external placeholders if available
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && player != null) {
            text = PlaceholderAPI.setPlaceholders(player, text);
        }
        
        return colorize(text);
    }
    
    /**
     * Count how many OPs are online (the elite squad)
     * @return Number of online OP players
     */
    private int getOnlineOPCount() {
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Check if player is AFK using Essentials (if it's even installed)
     * @param player The player to check
     * @return true if AFK, false otherwise
     */
    private boolean isPlayerAFK(Player player) {
        // Try to hook into Essentials (pray it's there)
        if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
            try {
                com.earth2me.essentials.Essentials ess = (com.earth2me.essentials.Essentials) 
                    Bukkit.getPluginManager().getPlugin("Essentials");
                
                if (ess != null) {
                    com.earth2me.essentials.User user = ess.getUser(player);
                    return user != null && user.isAfk();
                }
            } catch (Exception e) {
                // Essentials probably updated their API again, damn it
            }
        }
        
        // No Essentials or it failed, assume not AFK
        return false;
    }
    
    /**
     * Translate those sexy & color codes into actual colors
     * Also supports hex because we're living in 2024, not 2012
     * @param message The message with color codes
     * @return Colorized message that actually looks good
     */
    public String colorize(String message) {
        if (message == null) return "";
        
        // Handle hex colors (&x&R&R&G&G&B&B format)
        message = translateHexColorCodes(message);
        
        // Handle legacy & codes (the old reliable)
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
    
    /**
     * Translate hex color codes because apparently & is too mainstream
     * Format: &x&R&R&G&G&B&B
     * @param message Message with hex codes
     * @return Message with proper hex colors
     */
    private String translateHexColorCodes(String message) {
        // Pattern: &x&R&R&G&G&B&B where R, G, B are hex digits
        java.util.regex.Pattern hexPattern = java.util.regex.Pattern.compile("&x(&[0-9a-fA-F]){6}");
        java.util.regex.Matcher matcher = hexPattern.matcher(message);
        
        StringBuffer buffer = new StringBuffer();
        
        while (matcher.find()) {
            String hexCode = matcher.group();
            // Extract the 6 hex digits (ignore the &x and & symbols)
            String hex = hexCode.replaceAll("&", "").substring(1);
            
            // Create the net.md_5.bungee color
            String replacement = net.md_5.bungee.api.ChatColor.of("#" + hex).toString();
            matcher.appendReplacement(buffer, replacement);
        }
        
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}

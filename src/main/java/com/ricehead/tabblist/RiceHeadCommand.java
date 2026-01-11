package com.ricehead.tabblist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Command handler for /ricehead
 * Because every plugin needs a command, apparently
 */
public class RiceHeadCommand implements CommandExecutor {
    
    private final RiceTabbyList plugin;
    
    public RiceHeadCommand(RiceTabbyList plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, 
                           @NotNull String label, @NotNull String[] args) {
        
        // No args? Show help (because reading is hard)
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        // /ricehead reload - Reload the config (for when you inevitably screw it up)
        if (args[0].equalsIgnoreCase("reload")) {
            
            // Check permission (because not everyone deserves this power)
            if (!sender.hasPermission("ricetabby.reload")) {
                sender.sendMessage(plugin.getConfigManager()
                    .colorize("&cYou don't have permission to do that, buddy."));
                return true;
            }
            
            try {
                plugin.reloadPluginConfig();
                sender.sendMessage(plugin.getConfigManager()
                    .colorize("&aRice's Tabby List reloaded! Hopefully you fixed it this time."));
            } catch (Exception e) {
                sender.sendMessage(plugin.getConfigManager()
                    .colorize("&cFailed to reload! Check console for the inevitable error spam."));
                e.printStackTrace();
            }
            
            return true;
        }
        
        // Unknown subcommand (nice try though)
        sender.sendMessage(plugin.getConfigManager()
            .colorize("&cUnknown subcommand. Try /ricehead for help."));
        return true;
    }
    
    /**
     * Send help message (for the confused admins)
     * @param sender Who to send it to
     */
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(plugin.getConfigManager().colorize("&6&l=== Rice's Tabby List ==="));
        sender.sendMessage(plugin.getConfigManager().colorize("&e/ricehead reload &7- Reload the plugin config"));
        sender.sendMessage(plugin.getConfigManager().colorize("&7Internal placeholders: {tps}, {op_count}, {players}, {max_players}, {domain},"));
        sender.sendMessage(plugin.getConfigManager().colorize("&7{afk}, {afk_status}, {ram_used}, {ram_max}, {ram_free}, {ram_percent}, {ram_usage}"));
        sender.sendMessage(plugin.getConfigManager().colorize("&7External placeholders from PlaceholderAPI also supported"));
    }
}

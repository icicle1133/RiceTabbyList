package com.ricehead.tabblist;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Tracks TPS so you can watch your server performance go to shit in real-time
 * Spoiler alert: It's probably gonna be under 20
 */
public class TPSTracker {
    
    private final RiceTabbyList plugin;
    private BukkitTask task;
    
    // Store the last 3 tick measurements for averaging (because precision matters)
    private final long[] ticks = new long[3];
    private int tickIndex = 0;
    private long lastPoll = System.currentTimeMillis();
    
    private double currentTPS = 20.0; // Start optimistic, we all know it won't last
    
    public TPSTracker(RiceTabbyList plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Start tracking TPS every second (masochism at its finest)
     */
    public void startTracking() {
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long now = System.currentTimeMillis();
            long timeSpent = now - lastPoll;
            
            // Calculate ticks (20 ticks per second if server isn't dying)
            if (timeSpent > 0) {
                ticks[tickIndex++ % ticks.length] = timeSpent;
            }
            
            lastPoll = now;
            
            // Calculate average TPS (probably disappointing)
            calculateTPS();
        }, 20L, 20L); // Run every second (20 ticks)
    }
    
    /**
     * Calculate TPS from our tick measurements
     * Math time, baby!
     */
    private void calculateTPS() {
        long totalTime = 0;
        int validTicks = 0;
        
        // Average the tick times
        for (long tick : ticks) {
            if (tick > 0) {
                totalTime += tick;
                validTicks++;
            }
        }
        
        if (validTicks > 0) {
            double avgTime = (double) totalTime / validTicks;
            // TPS = 1000ms / average time per tick * 20
            currentTPS = Math.min(20.0, 1000.0 / avgTime * 20.0);
            
            // Round to 2 decimal places because we're not savages
            currentTPS = Math.round(currentTPS * 100.0) / 100.0;
        }
    }
    
    /**
     * Get current TPS (brace yourself for disappointment)
     * @return Current TPS value
     */
    public double getTPS() {
        return currentTPS;
    }
    
    /**
     * Get TPS as a formatted string because toString() is overrated
     * @return Formatted TPS string
     */
    public String getFormattedTPS() {
        return String.format("%.2f", currentTPS);
    }
    
    /**
     * Stop tracking when plugin disables (cleanup is important, kids)
     */
    public void stopTracking() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}

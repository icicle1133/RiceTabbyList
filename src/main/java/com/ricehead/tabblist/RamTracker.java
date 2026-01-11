package com.ricehead.tabblist;

/**
 * Tracks RAM usage because admins love watching their server eat memory
 * Spoiler: It's always using more than you think
 */
public class RamTracker {
    
    private final RiceTabbyList plugin;
    
    public RamTracker(RiceTabbyList plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Get used RAM in MB (prepare for disappointment)
     * @return Used RAM in megabytes
     */
    public long getUsedRamMB() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        return (totalMemory - freeMemory) / 1024 / 1024;
    }
    
    /**
     * @return Total allocated RAM in megabytes
     */
    public long getTotalRamMB() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() / 1024 / 1024;
    }
    
    /**
     * @return Max RAM in megabytes
     */
    public long getMaxRamMB() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.maxMemory() / 1024 / 1024;
    }
    
    /**
     * @return Free RAM in megabytes
     */
    public long getFreeRamMB() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.freeMemory() / 1024 / 1024;
    }
    
    /**
     * @return RAM usage percentage
     */
    public int getRamUsagePercent() {
        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();
        long max = runtime.maxMemory();
        return (int) ((used * 100) / max);
    }
    
    /**
     * @return Formatted RAM usage
     */
    public String getFormattedRamUsage() {
        return getUsedRamMB() + "/" + getMaxRamMB() + " MB";
    }
    
    /**
     * Get formatted RAM usage with percentage (e.g., "2048/4096 MB (50%)")
     * @return Formatted RAM usage with percentage
     */
    public String getFormattedRamUsageWithPercent() {
        return getUsedRamMB() + "/" + getMaxRamMB() + " MB (" + getRamUsagePercent() + "%)";
    }
}

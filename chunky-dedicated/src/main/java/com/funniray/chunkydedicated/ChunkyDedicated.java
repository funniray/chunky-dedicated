package com.funniray.chunkydedicated;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.popcraft.chunky.api.ChunkyAPI;

public final class ChunkyDedicated extends JavaPlugin {

    ChunkyAPI chunky;

    @Override
    public void onEnable() {
        // Plugin startup logic
        chunky = getServer().getServicesManager().load(ChunkyAPI.class);

        if (chunky == null) {
            getLogger().severe("Chunky is not loaded, disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getScheduler().runTaskTimer(this, ()->{
            if (isFinished()) {
                getLogger().info("No tasks were registered, server closing...");
                getServer().shutdown();
            }
        }, 200L, 200L);

        chunky.onGenerationComplete(event -> {
            boolean allTasksFinished = isFinished();
            if (!allTasksFinished) {
                getLogger().info("Tasks are remaining, server won't close");
                return;
            }
            getLogger().info("All tasks finished, closing server...");
            getServer().shutdown();
        });
    }

    private boolean isFinished() {
        return getServer().getWorlds().stream().map(World::getName).noneMatch(chunky::isRunning);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

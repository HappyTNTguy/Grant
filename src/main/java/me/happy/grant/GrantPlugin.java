package me.happy.grant;

import me.happy.grant.commands.GrantCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GrantPlugin extends JavaPlugin {

    public void onEnable() {
        getCommand("grant").setExecutor(new GrantCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new GrantCommand(), this);
    }
}

package com.gmail.ToughCookie.SPCustomHelp;

import com.gmail.ToughCookie.SPCustomHelp.PaginatedMessageList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SPCustomHelp
extends JavaPlugin
implements Listener {
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("help")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("SPCustomHelp.reload")) {
                    sender.sendMessage((Object)ChatColor.RED + "You are not allowed to do this.");
                    return true;
                }
                this.reloadConfig();
                sender.sendMessage((Object)ChatColor.GREEN + this.getName() + " " + this.getDescription().getVersion() + " reloaded.");
                return true;
            }
            int page = 0;
            if (args.length >= 1) {
                try {
                    page = Integer.parseInt(args[0]) - 1;
                }
                catch (NumberFormatException e) {
                    page = 0;
                }
            }
            ArrayList<String> list = new ArrayList<String>();
            Map commandsMap = this.getConfig().getConfigurationSection("commands").getValues(true);
            for (Map.Entry entry : commandsMap.entrySet()) {
                list.add((Object)ChatColor.GOLD + (String)entry.getKey() + (Object)ChatColor.WHITE + ": " + entry.getValue());
            }
            new PaginatedMessageList(this.getConfig().getString("header"), this.getConfig().getString("footer")).send(sender, list, page);
            return true;
        }
        return false;
    }
}


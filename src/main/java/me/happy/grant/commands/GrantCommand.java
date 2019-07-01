package me.happy.grant.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class GrantCommand
        implements CommandExecutor, Listener
{
    private static String target;
    private static String rank;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You need to be a player.");
            return true;
        }

        Player player = (Player)sender;


        if(!player.hasPermission("command.grant")) {
            player.sendMessage(ChatColor.RED + "Invalid Usage.");
            return true;
        }

        if (args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Invalid arguemnt. /" + label + " <player>");
            return true;
        }
        target = args[0];

        createInventory(player);

        return true;
    }

    public void createInventory(Player player)
    {
        Inventory i = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Ranks");
        ItemStack rankMaterial = new ItemStack(Material.WOOL);
        ItemMeta rankMaterialMeta = rankMaterial.getItemMeta();
        int number = 0;
        for (PermissionGroup i1 : PermissionsEx.getPermissionManager().getGroupList())
        {
            rankMaterialMeta.setDisplayName(i1.getName());
            rankMaterial.setItemMeta(rankMaterialMeta);
            i.setItem(number, rankMaterial);
            number += 1;
        }
        player.openInventory(i);
    }

    public void durationInv(Player player)
    {
        Inventory i = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Duration");
        ItemStack threed = new ItemStack(Material.WOOL);
        ItemStack thirtyd = new ItemStack(Material.WOOL);
        ItemStack perm = new ItemStack(Material.WOOL);
        ItemStack uno = new ItemStack(Material.WOOL);
        ItemMeta threedMeta = threed.getItemMeta();
        ItemMeta thirtyDMEta = thirtyd.getItemMeta();
        ItemMeta permMeta = perm.getItemMeta();
        ItemMeta unoMeta = uno.getItemMeta();
        unoMeta.setDisplayName(ChatColor.RED + "One day");
        threedMeta.setDisplayName(ChatColor.RED + "3 days");
        thirtyDMEta.setDisplayName(ChatColor.RED + "30 days");
        permMeta.setDisplayName(ChatColor.RED + "Perm");
        threed.setItemMeta(threedMeta);
        thirtyd.setItemMeta(thirtyDMEta);
        perm.setItemMeta(permMeta);
        uno.setItemMeta(unoMeta);
        i.setItem(0, uno);
        i.setItem(1, threed);
        i.setItem(2, thirtyd);
        i.setItem(3, perm);
        player.openInventory(i);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getInventory().getName().equals(ChatColor.GREEN + "Ranks"))
        {
            Player player = (Player)e.getWhoClicked();
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().hasItemMeta())
            {
                rank = e.getCurrentItem().getItemMeta().getDisplayName();
                durationInv(player);
            }
        }
        else if (e.getInventory().getName().equals(ChatColor.GREEN + "Duration"))
        {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED + "One day"))
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target + " group add " + rank + " \"\" 86400");
                p.sendMessage(ChatColor.GREEN + "Successfully gave " + ChatColor.YELLOW + target + " " + ChatColor.BLUE + rank + ChatColor.GREEN + " rank for a days!");
                p.closeInventory();
            }
            else if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED + "3 days"))
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target + " group add " + rank + " \"\" 259200");
                p.sendMessage(ChatColor.GREEN + "Successfully gave " + ChatColor.YELLOW + target + " " + ChatColor.BLUE + rank + ChatColor.GREEN + " rank for 3 days!");
                p.closeInventory();
            }
            else if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED + "30 days"))
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target + " group add " + rank + " \"\" 2592000");
                p.sendMessage(ChatColor.GREEN + "Successfully gave " + ChatColor.YELLOW + target + " " + ChatColor.BLUE + rank + ChatColor.GREEN + " rank for 30 days!");
                p.closeInventory();
            }
            else if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED + "Perm"))
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target + " group set " + rank);
                p.sendMessage(ChatColor.GREEN + "Successfully gave " + ChatColor.YELLOW + target + " " + ChatColor.BLUE + rank + ChatColor.GREEN + " rank!");
                p.closeInventory();
            }
        }
    }
}

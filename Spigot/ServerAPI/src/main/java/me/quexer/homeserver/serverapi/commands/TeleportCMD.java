package me.quexer.homeserver.serverapi.commands;

import me.quexer.homeserver.serverapi.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCMD implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Player p = (Player)sender;
        if (p.hasPermission("teleport")) {
            Player t;
            if (args.length == 1) {
                t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage(ServerAPI.getPrefix() + "§cDieser Spieler ist nicht Online!");
                    return true;
                }

                p.teleport(t);
                p.sendMessage(ServerAPI.getPrefix() + "§7Du hast dich zu " + t.getDisplayName() + " §7teleportiert");
            } else if (args.length == 2) {
                t = Bukkit.getPlayer(args[0]);
                Player e = Bukkit.getPlayer(args[1]);
                if (t == null || e == null) {
                    p.sendMessage(ServerAPI.getPrefix() + "§cDieser Spieler ist nicht Online!");
                    return true;
                }

                t.teleport(e);
                p.sendMessage(ServerAPI.getPrefix() + "§7Du hast " + t.getDisplayName() + " §7zu " + e.getDisplayName() + " §7teleportiert");
            } else {
                p.sendMessage(ServerAPI.getPrefix() + "§7Benutze§8: §7/Tp <Spieler> <Spieler>");
            }
        }

        return false;
    }
    
}

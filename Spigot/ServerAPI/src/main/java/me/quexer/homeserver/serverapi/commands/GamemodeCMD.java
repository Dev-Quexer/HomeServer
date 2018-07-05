package me.quexer.homeserver.serverapi.commands;

import me.quexer.homeserver.serverapi.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Player p = (Player)sender;
        if (p.hasPermission("gamemode")) {
            if (args.length == 1) {
                boolean var6 = false;

                int s;
                try {
                    s = Integer.parseInt(args[0]);
                } catch (NumberFormatException var10) {
                    p.sendMessage(ServerAPI.getPrefix() + "§cDu musst eine Zahl angeben");
                    return true;
                }

                if (s == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(ServerAPI.getPrefix() + "§7Dein Spielmodus wurde zu §eSurvival §7geändert.");
                } else if (s == 1) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(ServerAPI.getPrefix() + "§7Dein Spielmodus wurde zu §eKreativ §7geändert.");
                } else if (s == 2) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(ServerAPI.getPrefix() + "§7Dein Spielmodus wurde zu §eAdventure §7geändert.");
                } else if (s == 3) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(ServerAPI.getPrefix() + "§7Dein Spielmodus wurde zu §eSpectator §7geändert.");
                } else {
                    p.sendMessage(ServerAPI.getPrefix() + "§7Benutze§8: §7/Gamemode <Spieler> <0/1/2/3>");
                }
            } else if (args.length == 2) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage(ServerAPI.getPrefix() + "§cDieser Spieler ist nicht Online!");
                    return true;
                }

                boolean var7 = false;

                int s;
                try {
                    s = Integer.parseInt(args[1]);
                } catch (NumberFormatException var9) {
                    p.sendMessage(ServerAPI.getPrefix() + "§cDu musst eine Zahl angeben");
                    return true;
                }

                if (s == 0) {
                    t.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eSurvival §7geändert");
                } else if (s == 1) {
                    t.setGameMode(GameMode.CREATIVE);
                    p.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eKreativ §7geändert");
                } else if (s == 2) {
                    t.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eAdventure §7geändert");
                } else if (s == 3) {
                    t.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eSpectator §7geändert");
                } else {
                    p.sendMessage(ServerAPI.getPrefix() + "§7Benutze§8: §7/Gamemode <Spieler> <0/1/2/3>");
                }
            } else {
                p.sendMessage(ServerAPI.getPrefix() + "§7Benutze§8: §7/Gamemode <Spieler> <0/1/2/3>");
            }
        }

        return false;
    }
    
}

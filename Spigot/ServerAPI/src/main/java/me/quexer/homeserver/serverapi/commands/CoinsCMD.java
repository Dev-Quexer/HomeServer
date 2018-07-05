package me.quexer.homeserver.serverapi.commands;

import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CoinsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        new AsyncTask(() -> {
        if(strings.length == 1) {
            ServerAPI.getUserManager().getUser(Bukkit.getOfflinePlayer(strings[1]), user -> {
                commandSender.sendMessage(ServerAPI.getCoinsPrefix()+"§7Der Spieler "+Bukkit.getOfflinePlayer(strings[1]).getName()+" §7hat §e"+user.getCoins()+" §7Coins§8.");
            });
        } else {
             ServerAPI.getUserManager().getUser(Bukkit.getOfflinePlayer(commandSender.getName()), user -> {
                 commandSender.sendMessage(ServerAPI.getCoinsPrefix()+"§7Du hast §e"+user.getCoins()+" §7Coins§8.");
             });
         }
        });
        return true;
    }
}

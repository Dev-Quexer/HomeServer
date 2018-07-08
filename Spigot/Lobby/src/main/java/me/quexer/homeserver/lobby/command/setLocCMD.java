package me.quexer.homeserver.lobby.command;

import me.quexer.homeserver.lobby.Lobby;
import me.quexer.homeserver.serverapi.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setLocCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender.hasPermission("setLoc")) {
            if(args.length == 1) {
                ServerAPI.getLocationAPI().setLocation(args[0].toLowerCase(), (Player) sender);
                sender.sendMessage(Lobby.getPrefix()+"§7Du hast die Location §e"+args[0]+" §7erfolgreich gesetzt§8.");
            } else {
                sender.sendMessage(Lobby.getPrefix()+"§7Benutze§8: §c/setLoc <Location>");
            }
        }

        return true;
    }
}

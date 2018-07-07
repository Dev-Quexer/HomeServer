package me.quexer.homeserver.proxysystem.commands;

import me.quexer.homeserver.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class HelpCMD extends Command {


    public HelpCMD() {
        super("help", "NONE", "?", "hilfe");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ProxySystem.getPrefix() + "§7Informationen zum §eFarMC.de §7Netzwerk");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/report §8┃ §7Reporte einen Spieler");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/party §8┃ §7Erstelle eine Party mit anderen Spielern");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/friend §8┃ §7Schließe Freundschaften mit Spielern");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/ping §8┃ §7Zeigt dir deinen Ping");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/premium §8┃ §7Informationen zum Premium Rang");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/premium+ §8┃ §7Informationen zum Premium+ Rang");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/youtube §8┃ §7Informationen zum Youtuber Rang");
        sender.sendMessage(ProxySystem.getPrefix() + "§a/ts §8┃ §7Informationen zu unserem TeamSpeak");
    }
}

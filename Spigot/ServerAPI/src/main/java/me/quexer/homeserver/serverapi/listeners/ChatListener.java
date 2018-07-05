package me.quexer.homeserver.serverapi.listeners;

import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.utils.NickAPI;
import me.quexer.homeserver.serverapi.utils.Tablist;
import me.quexer.homeserver.serverapi.utils.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import java.awt.peer.LabelPeer;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(Tablist.isSetChatPrefix()) {
            if(NickAPI.hasNick(e.getPlayer())) {
                e.setFormat(Rank.PREMIUM.getPrefix() +e.getPlayer().getName()+ " §8➜ §7" + e.getMessage());
            } else {
                e.setFormat(e.getPlayer().getDisplayName() + " §8➜ §7" + e.getMessage());
            }
        }
        String msg = e.getMessage().replaceAll("%", "%%");
        e.setMessage(msg);
    }



    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String m = e.getMessage().split(" ")[0];
        new AsyncTask(() -> {
        if (e.getMessage().startsWith("/")) {
            if (p.hasPermission("admin") && e.getMessage().startsWith("/cs") || e.getMessage().startsWith("/cloudserver")) {
                e.setCancelled(false);
                return;
            }

            HelpTopic helptopic = Bukkit.getHelpMap().getHelpTopic(m);
            if (helptopic == null) {
                e.setCancelled(true);
                p.sendMessage(ServerAPI.getPrefix() + "§7Der Befehl wurde nicht gefunden. §8[§a" + e.getMessage() + "§8]");
            }

            if (e.getMessage().startsWith("/help") || e.getMessage().startsWith("/pl") || e.getMessage().startsWith("/bukkit:") || e.getMessage().startsWith("/minecraft:") || e.getMessage().startsWith("/ver") || e.getMessage().startsWith("/spigot:") || e.getMessage().startsWith("/about") || e.getMessage().startsWith("/tps") || e.getMessage().startsWith("/?") || e.getMessage().startsWith("/me")) {
                if (!p.hasPermission("bypass.commands")) {
                    e.setCancelled(true);
                    p.sendMessage(ServerAPI.getPrefix() + "§7Informationen zum §eFarMC.de §7Netzwerk");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/report §8┃ §7Reporte einen Spieler");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/party §8┃ §7Erstelle eine Party mit anderen Spielern");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/friend §8┃ §7Schließe Freundschaften mit Spielern");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/ping §8┃ §7Zeigt dir deinen Ping");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/premium §8┃ §7Informationen zum Premium Rang");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/premium+ §8┃ §7Informationen zum Premium+ Rang");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/youtube §8┃ §7Informationen zum Youtuber Rang");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/ts §8┃ §7Informationen zu unserem TeamSpeak");
                } else if (e.getMessage().startsWith("/help")) {
                    e.setCancelled(true);
                    p.sendMessage(ServerAPI.getPrefix() + "§7Informationen zum §eFarMC.de §7Netzwerk");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/report §8┃ §7Reporte einen Spieler");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/party §8┃ §7Erstelle eine Party mit anderen Spielern");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/friend §8┃ §7Schließe Freundschaften mit Spielern");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/ping §8┃ §7Zeigt dir deinen Ping");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/premium §8┃ §7Informationen zum Premium Rang");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/premium+ §8┃ §7Informationen zum Premium+ Rang");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/youtube §8┃ §7Informationen zum Youtuber Rang");
                    p.sendMessage(ServerAPI.getPrefix() + "§a/ts §8┃ §7Informationen zu unserem TeamSpeak");
                } else {
                    e.setCancelled(false);
                }
            }
        }
        });

    }

}

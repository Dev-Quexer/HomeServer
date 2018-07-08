package me.quexer.homeserver.serverapi.listeners;

import de.dytanic.cloudnet.api.CloudAPI;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.utils.NickAPI;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import me.quexer.homeserver.serverapi.utils.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class JoinQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getInstance(), () -> {
            ServerAPI.getUserManager().getUser(e.getPlayer(), user -> {
                ServerAPI.getInstance().setMetadata(e.getPlayer(), "user", user);




            });
            if(ServerAPI.isNickOnThisServer()) {
                if (e.getPlayer().hasPermission("nick.nick")) {
                    ServerAPI.getUserManager().getUser(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId()).getPlayer(), user -> {
                        if(user.isNick()) {
                            NickAPI.setRandomNick(e.getPlayer());
                        }
                    });
                }
            }
        });

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
            User user = (User) e.getPlayer().getMetadata("user").get(0).value();
            user.setLastPlayed(System.currentTimeMillis());
            ServerAPI.getUserManager().updateUser(user, userUpdated -> {

            });
            ServerAPI.getInstance().removeMetadata(e.getPlayer(), "user");

    }

}

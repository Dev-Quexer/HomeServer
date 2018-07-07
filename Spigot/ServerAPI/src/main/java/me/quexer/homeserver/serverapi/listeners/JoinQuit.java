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
        ScoreboardManager scoreboardManager = new ScoreboardManager();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("&a", 3);
        hashMap.put("&7Name&8: ", 2);
        hashMap.put("&a", 1);

        scoreboardManager.setBoard(e.getPlayer(), "&lHOMESERVER.NET", hashMap);
        Bukkit.getScheduler().runTaskLaterAsynchronously(ServerAPI.getInstance(), () -> {
            scoreboardManager.createTeam(e.getPlayer(),"Name", "&7Name&8: ", "&b", ""+e.getPlayer().getLevel());
        }, 60);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (e.getPlayer().hasMetadata("user")) {
            User user = (User) e.getPlayer().getMetadata("user").get(0).value();
            user.setLastPlayed(System.currentTimeMillis());
            ServerAPI.getInstance().removeMetadata(e.getPlayer(), "user");
            ServerAPI.getUserManager().updateUser(user, userUpdated -> {

            });
        }
    }

}

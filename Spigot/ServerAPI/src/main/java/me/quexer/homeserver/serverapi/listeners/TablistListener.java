package me.quexer.homeserver.serverapi.listeners;

import me.quexer.homeserver.serverapi.utils.Tablist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TablistListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(Tablist.isSetTablist()) {
            Tablist.setPrefix(e.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Tablist.isSetTablist()) {
            if (Tablist.t.containsKey(p.getUniqueId())) {
                Tablist.sb.getTeam((String) Tablist.t.get(p.getUniqueId())).removePlayer(p);
                Tablist.t.remove(p.getUniqueId());
            }
        }

    }


}

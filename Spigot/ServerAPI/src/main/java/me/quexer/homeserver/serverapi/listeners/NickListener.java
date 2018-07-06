package me.quexer.homeserver.serverapi.listeners;

import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.events.PlayerNickEvent;
import me.quexer.homeserver.serverapi.events.PlayerRemoveNickEvent;
import me.quexer.homeserver.serverapi.utils.NickAPI;
import me.quexer.homeserver.serverapi.utils.Tablist;
import me.quexer.homeserver.serverapi.utils.enums.SoundType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class NickListener implements Listener {

    @EventHandler
    public void onNick(PlayerNickEvent e) {
        e.getPlayer().sendMessage(ServerAPI.getPrefix()+"§7Dein neuer §eNickname §7lautet§8: §6"+e.getNick());
        ServerAPI.getSoundManager().play(e.getPlayer(), SoundType.GOOD);
        if(Tablist.isSetTablist()) {
            Tablist.setPrefix(e.getPlayer());
        }

    }

    @EventHandler
    public void onNickRemove(PlayerRemoveNickEvent e) {
        e.getPlayer().sendMessage(ServerAPI.getPrefix()+"§4Dein Nickname wurde entfernt§8!");
        ServerAPI.getSoundManager().play(e.getPlayer(), SoundType.BAD);
        if(Tablist.isSetTablist()) {
           Tablist.setPrefix(e.getPlayer());
        }
    }




}

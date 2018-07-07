package me.quexer.homeserver.proxysystem.listeners;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.BanPlayerManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MuteListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
            if (!event.getMessage().startsWith("/")) {
                ProxySystem.getBanPlayerManager().getBanPlayer(((ProxiedPlayer) event.getSender()).getUniqueId().toString(), banPlayer -> {
                    ProxySystem.getMuteManager().isMuteed(banPlayer, muted -> {
                        if (muted) {
                            if (System.currentTimeMillis() <= banPlayer.getMuteEnd() || banPlayer.getMuteEnd() == -2) {
                                event.setCancelled(true);
                                ProxiedPlayer player = (ProxiedPlayer) event.getSender();
                                player.sendMessage("§8§m-----------------------------------------");
                                player.sendMessage(ProxySystem.getMutePrefix() + "§7Du wurdest gemutet§8!");
                                player.sendMessage(ProxySystem.getMutePrefix() + "§7Von§8: §e" + banPlayer.getMuteFrom());
                                player.sendMessage(ProxySystem.getMutePrefix() + "§7Grund§8: §e" + banPlayer.getMuteReason());
                                player.sendMessage(ProxySystem.getMutePrefix() + "§7Gemutet bis§8: §e" + ProxySystem.getMuteManager().getDate(banPlayer.getMuteEnd()));
                                player.sendMessage(ProxySystem.getMutePrefix() + "§7BanPoints§8: §e" + banPlayer.getBanPoints());
                                player.sendMessage("§8§m-----------------------------------------");
                            } else {
                                ProxySystem.getMuteManager().unMutePlayer(banPlayer, "[CONSOLE]", "Automatischer Unban");
                            }
                        }
                    });
                });
            }
        });
    }

}

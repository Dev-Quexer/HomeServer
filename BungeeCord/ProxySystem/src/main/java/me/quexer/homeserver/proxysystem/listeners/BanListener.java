package me.quexer.homeserver.proxysystem.listeners;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.BanPlayerManager;
import me.quexer.homeserver.proxysystem.utils.enitys.BanPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class BanListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent event) {
        event.registerIntent(ProxySystem.getInstance());
        ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
        ProxySystem.getBanPlayerManager().getBanPlayer(event.getConnection().getUniqueId().toString(), banPlayer -> {

            ProxySystem.getBanManager().isBanned(banPlayer, banned -> {
                if(banned) {
                    if (banPlayer.getBanEnd() > System.currentTimeMillis() || banPlayer.getBanEnd() == -2) {
                        ProxySystem.getBungeeCord().getScheduler().schedule(ProxySystem.getInstance(), () -> {
                        event.getConnection().disconnect("§8● §c§lHomeServer§8.§c§lnet §8●\n\n" +
                                "§cDu wurdest vom §eNetzwerk §cgebannt§8!\n" +
                                "\n" +
                                "§7Grund§8: §e" + banPlayer.getBanReason() + "\n" +
                                "§7Gebannt von§8: §e" + banPlayer.getBanFrom() + "\n" +
                                "§7Gebannt bis§8: §e" + ProxySystem.getBanManager().getDate(banPlayer.getBanEnd()) + "\n" +
                                "§7BanPoints§8: §e" + banPlayer.getBanPoints() + "\n" +
                                "\n" +
                                "§7Du kannst einen §eEntbannungsantrag §7im Forum stellen");
                        },  200, TimeUnit.MILLISECONDS);
                    } else {
                        ProxySystem.getBanManager().unBanPlayer(banPlayer, "Automatischer Unban", "[CONSOLE]");
                    }
                }
            });
        });
        });
        event.completeIntent(ProxySystem.getInstance());
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        BanPlayer banPlayer = BanPlayerManager.banPlayerHashMap.get(event.getPlayer().getUniqueId().toString());
        ProxySystem.getBanPlayerManager().updateUser(banPlayer, banPlayer1 -> {
            System.out.println("User "+event.getPlayer().getName()+" saved");
        });
        BanPlayerManager.banPlayerHashMap.remove(event.getPlayer().getUniqueId().toString());
    }
}

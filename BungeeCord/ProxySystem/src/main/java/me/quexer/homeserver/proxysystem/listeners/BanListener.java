package me.quexer.homeserver.proxysystem.listeners;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.BanPlayerManager;
import me.quexer.homeserver.proxysystem.utils.enitys.BanPlayer;
import me.quexer.homeserver.proxysystem.utils.uuid.UUIDFetcher;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BanListener implements Listener {



    @EventHandler
    public void onLogin(PreLoginEvent event) {
        String str = UUIDFetcher.getUUID(event.getConnection().getName()).toString();
        event.registerIntent(ProxySystem.getInstance());
        ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
                ProxySystem.getBanPlayerManager().getBanPlayer(str, banPlayer -> {


                        if(banPlayer.getBanEnd() != -1) {
                            if (System.currentTimeMillis() <= banPlayer.getBanEnd() || banPlayer.getBanEnd() == -2) {

                                event.setCancelReason("§8● §c§lHomeServer§8.§c§lnet §8●\n\n" +
                                        "§cDu wurdest vom §eNetzwerk §cgebannt§8!\n" +
                                        "\n" +
                                        "§8§m------------------------------\n" +
                                        "§7Grund§8: §e" + banPlayer.getBanReason() + "\n" +
                                        "§7Gebannt von§8: §e" + banPlayer.getBanFrom() + "\n" +
                                        "§7Gebannt bis§8: §e" + ProxySystem.getBanManager().getDate(banPlayer.getBanEnd()) + "\n" +
                                        "§7BanPoints§8: §e" + banPlayer.getBanPoints() + "\n" +
                                        "§8§m------------------------------\n" +
                                        "\n" +
                                        "§8(§7Ab §e5 §7BanPoints ist der Ban §4§lPERMANENT§8)\n" +
                                        "§7Du kannst einen §eEntbannungsantrag §7im Forum stellen");
                                event.setCancelled(true);
                                event.completeIntent(ProxySystem.getInstance());
                            } else {
                                ProxySystem.getBanManager().unBanPlayer(banPlayer, "Automatischer Unban", "[CONSOLE]");
                                event.setCancelled(false);
                                event.completeIntent(ProxySystem.getInstance());
                            }
                        } else {
                            event.setCancelled(false);
                            event.completeIntent(ProxySystem.getInstance());
                        }


                });
        });

    }

/*
    @EventHandler
    public void onLogin(PreLoginEvent event) {
        event.registerIntent(ProxySystem.getInstance());
        ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
            ProxySystem.getBungeeCord().getScheduler().schedule(ProxySystem.getInstance(), () -> {
                ProxySystem.getBanPlayerManager().getBanPlayer(event.getConnection().getUniqueId().toString(), banPlayer -> {

                    ProxySystem.getBanManager().isBanned(banPlayer, aBoolean -> {
                        if(aBoolean) {
                            if (System.currentTimeMillis() <= banPlayer.getBanEnd() || banPlayer.getBanEnd() == -2) {
                                event.setCancelled(true);
                                event.setCancelReason("§8● §c§lHomeServer§8.§c§lnet §8●\n\n" +
                                        "§cDu wurdest vom §eNetzwerk §cgebannt§8!\n" +
                                        "\n" +
                                        "§7Grund§8: §e" + banPlayer.getBanReason() + "\n" +
                                        "§7Gebannt von§8: §e" + banPlayer.getBanFrom() + "\n" +
                                        "§7Gebannt bis§8: §e" + ProxySystem.getBanManager().getDate(banPlayer.getBanEnd()) + "\n" +
                                        "§7BanPoints§8: §e" + banPlayer.getBanPoints() + "\n" +
                                        "\n" +
                                        "§7Du kannst einen §eEntbannungsantrag §7im Forum stellen");


                            } else {
                                ProxySystem.getBanManager().unBanPlayer(banPlayer, "Automatischer Unban", "[CONSOLE]");
                            }
                        }
                    });

                });
            }, 1, TimeUnit.MILLISECONDS);
        });

    }

 */

   /*@EventHandler
    public void onLogin(PostLoginEvent event) {

    ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
        ProxySystem.getBungeeCord().getScheduler().schedule(ProxySystem.getInstance(), () -> {
        ProxySystem.getBanPlayerManager().getBanPlayer(event.getPlayer().getUniqueId().toString(), banPlayer -> {

            ProxySystem.getBanManager().isBanned(banPlayer, aBoolean -> {
                if(aBoolean) {
                    if (System.currentTimeMillis() <= banPlayer.getBanEnd() || banPlayer.getBanEnd() == -2) {
                        event.getPlayer().disconnect("§8● §c§lHomeServer§8.§c§lnet §8●\n\n" +
                                "§cDu wurdest vom §eNetzwerk §cgebannt§8!\n" +
                                "\n" +
                                "§7Grund§8: §e" + banPlayer.getBanReason() + "\n" +
                                "§7Gebannt von§8: §e" + banPlayer.getBanFrom() + "\n" +
                                "§7Gebannt bis§8: §e" + ProxySystem.getBanManager().getDate(banPlayer.getBanEnd()) + "\n" +
                                "§7BanPoints§8: §e" + banPlayer.getBanPoints() + "\n" +
                                "\n" +
                                "§7Du kannst einen §eEntbannungsantrag §7im Forum stellen");


                    } else {
                        ProxySystem.getBanManager().unBanPlayer(banPlayer, "Automatischer Unban", "[CONSOLE]");
                    }
                }
            });

        });
        }, 1, TimeUnit.MILLISECONDS);
    });

    }
    */

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        BanPlayer banPlayer = BanPlayerManager.banPlayerHashMap.get(event.getPlayer().getUniqueId().toString());
        ProxySystem.getBanPlayerManager().updateUser(banPlayer, banPlayer1 -> {
            System.out.println("User "+event.getPlayer().getName()+" saved");
        });
        BanPlayerManager.banPlayerHashMap.remove(event.getPlayer().getUniqueId().toString());
    }
}

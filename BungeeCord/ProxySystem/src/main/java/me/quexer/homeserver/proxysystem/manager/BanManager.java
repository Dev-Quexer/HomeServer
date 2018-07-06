package me.quexer.homeserver.proxysystem.manager;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.BanPlayerManager;
import me.quexer.homeserver.proxysystem.utils.enitys.BanPlayer;
import me.quexer.homeserver.proxysystem.utils.uuid.UUIDFetcher;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BanManager {


    public void isBanned(BanPlayer banPlayer, Consumer<Boolean> consumer) {
        if(banPlayer.getBanReason() != "NONE") {
            consumer.accept(true);
        } else {
            consumer.accept(false);
        }
    }


    public void banPlayer(BanPlayer banPlayer, String reason, String von, long Stunden) {
        ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
        isBanned(banPlayer,  isBanned -> {
            if(!isBanned) {
                long end;
                int banPoints = banPlayer.getBanPoints()+1;
                if(banPoints >= 4) {
                    end = -1;
                } else {
                    long now = System.currentTimeMillis();
                    end = now + Stunden * 1000 * 60 * 60;
                }
                banPlayer.setBanEnd(end);
                banPlayer.setBanReason(reason);
                banPlayer.setBanPoints(banPoints);
                banPlayer.setBanFrom(von);



                ProxySystem.getBanPlayerManager().updateUser(banPlayer, banPlayer1 -> {

                    UUIDFetcher.getName(UUID.fromString(banPlayer1.getUuid()), s -> {
                        for (ProxiedPlayer p : ProxySystem.getInstance().getProxy().getPlayers()) {
                            if (p.hasPermission("ban.notify")) {
                                p.sendMessage("§8§m-----------------------------------------");
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7Der Spieler §c" + s + " §7wurde gebannt§8!");
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7Von§8: §e" + von);
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7Grund§8: §e" + reason);
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7Verbleibende Zeit§8: §e" + getDate(end));
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7BanPoints§8: §e" + banPoints);
                                p.sendMessage("§8§m-----------------------------------------");
                            }
                        }
                    });

                    ProxiedPlayer player = ProxySystem.getBungeeCord().getPlayer(UUID.fromString(banPlayer.getUuid()));
                    if(player != null && player.isConnected()) {
                        player.disconnect("§8● §c§lHomeServer§8.§c§lnet §8●\n" +
                                "§cDu wurdest vom §eNetzwerk §cgebannt§8!\n" +
                                "\n" +
                                "§7Grund§8: §e"+reason+"\n" +
                                "§7Gebannt von§8: §e"+von+"\n" +
                                "§7Verbleibende Zeit§8: §e"+getDate(end)+"\n" +
                                "§7BanPoints§8: §e"+banPoints+"\n" +
                                "\n" +
                                "§7Du kannst einen §eEntbannungsantrag §cim Forum stellen");
                    }

                });
            } else {
                ProxySystem.getBungeeCord().getPlayer(von).sendMessage(ProxySystem.getBanPrefix()+"§cDieser Spieler ist bereits §egebannt§8!");
                ProxySystem.getBungeeCord().getPlayer(von).sendMessage(ProxySystem.getBanPrefix()+"§7Für eine Info§8: §e§l/BanInfo <Player>");
            }
            BanPlayerManager.banPlayerHashMap.put(banPlayer.getUuid(), banPlayer);
        });
        });



    }

    public void unBanPlayer(BanPlayer banPlayer, String von, String reason) {
        banPlayer.setBanEnd(-1);
        banPlayer.setBanFrom("NONE");
        banPlayer.setBanPoints(0);
        banPlayer.setBanReason("NONE");
        banPlayer.setMuteEnd(-1);
        banPlayer.setMuteFrom("NONE");
        banPlayer.setMutePoints(0);
        banPlayer.setMuteReason("NONE");
        UUIDFetcher.getName(UUID.fromString(banPlayer.getUuid()), s -> {
            for (ProxiedPlayer p : ProxySystem.getInstance().getProxy().getPlayers()) {
                if (p.hasPermission("ban.notify")) {
                    p.sendMessage("§8§m-----------------------------------------");
                    p.sendMessage(ProxySystem.getBanPrefix() + "§7Der Spieler §c" + s + " §7wurde §aentbannt§8!");
                    p.sendMessage(ProxySystem.getBanPrefix() + "§7Von§8: §e" + von);
                    p.sendMessage(ProxySystem.getBanPrefix() + "§7Grund§8: §e" + reason);
                    p.sendMessage("§8§m-----------------------------------------");
                }
            }
        });
        BanPlayerManager.banPlayerHashMap.put(banPlayer.getUuid(), banPlayer);
    }

    public String getDate(long millis) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        now.setTime(millis-System.currentTimeMillis());
        return sdfDate.format(now);
    }


}

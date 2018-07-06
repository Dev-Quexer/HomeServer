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
        if(banPlayer.getBanEnd() != -1) {
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
                if(banPoints >= 5 || Stunden == -2) {
                    end = -2;
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
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7Gebannt bis§8: §e" + getDate(end));
                                p.sendMessage(ProxySystem.getBanPrefix() + "§7BanPoints§8: §e" + banPoints);
                                p.sendMessage("§8§m-----------------------------------------");
                            }
                        }
                    });

                    ProxiedPlayer player = ProxySystem.getBungeeCord().getPlayer(UUID.fromString(banPlayer.getUuid()));
                    if(player != null && player.isConnected()) {
                        player.disconnect("§8● §c§lHomeServer§8.§c§lnet §8●\n\n" +
                                "§cDu wurdest vom §eNetzwerk §cgebannt§8!\n" +
                                "\n" +
                                "§8§m------------------------------\n" +
                                "§7Grund§8: §e"+reason+"\n" +
                                "§7Gebannt von§8: §e"+von+"\n" +
                                "§7Gebannt bis§8: §e"+getDate(end)+"\n" +
                                "§7BanPoints§8: §e"+banPoints+"\n" +
                                "§8§m------------------------------\n" +
                                "\n" +
                                "§8(§7Ab §e5 §7BanPoints ist der Ban §4§lPERMANENT§8)\n" +
                                "§7Du kannst einen §eEntbannungsantrag §7im Forum stellen");
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
        banPlayer.setBanReason("NONE");
        ProxySystem.getBanPlayerManager().updateUser(banPlayer, banPlayer1 -> {
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
        });


    }

    public String getDate(long millis) {
        if(millis == -2) {
            return "§4§lPERMANENT";
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date now = new Date();
            now.setTime(millis);
            return sdfDate.format(now);
        }
    }


}

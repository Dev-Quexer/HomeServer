package me.quexer.homeserver.proxysystem.manager;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.BanPlayerManager;
import me.quexer.homeserver.proxysystem.utils.enitys.BanPlayer;
import me.quexer.homeserver.proxysystem.utils.uuid.UUIDFetcher;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

public class MuteManager {


    public void isMuteed(BanPlayer MutePlayer, Consumer<Boolean> consumer) {
        if(MutePlayer.getMuteEnd() != -1) {
            consumer.accept(true);
        } else {
            consumer.accept(false);
        }
    }


    public void MutePlayer(BanPlayer MutePlayer, String reason, String von, long Stunden) {
        ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
            isMuteed(MutePlayer,  isMuteed -> {
                if(!isMuteed) {
                    long end;
                    int MutePoints = MutePlayer.getBanPoints()+1;
                    if(MutePoints >= 5 || Stunden == -2) {
                        end = -2;
                    } else {
                        long now = System.currentTimeMillis();
                        end = now + Stunden * 1000 * 60 * 60;
                    }
                    MutePlayer.setMuteEnd(end);
                    MutePlayer.setMuteReason(reason);
                    MutePlayer.setBanPoints(MutePoints);
                    MutePlayer.setMuteFrom(von);



                    ProxySystem.getBanPlayerManager().updateUser(MutePlayer, MutePlayer1 -> {

                        UUIDFetcher.getName(UUID.fromString(MutePlayer1.getUuid()), s -> {
                            for (ProxiedPlayer p : ProxySystem.getInstance().getProxy().getPlayers()) {
                                if (p.hasPermission("Mute.notify")) {
                                    p.sendMessage("§8§m-----------------------------------------");
                                    p.sendMessage(ProxySystem.getMutePrefix() + "§7Der Spieler §c" + s + " §7wurde gemutet§8!");
                                    p.sendMessage(ProxySystem.getMutePrefix() + "§7Von§8: §e" + von);
                                    p.sendMessage(ProxySystem.getMutePrefix() + "§7Grund§8: §e" + reason);
                                    p.sendMessage(ProxySystem.getMutePrefix() + "§7Gemutet bis§8: §e" + getDate(end));
                                    p.sendMessage(ProxySystem.getMutePrefix() + "§7BanPoints§8: §e" + MutePoints);
                                    p.sendMessage("§8§m-----------------------------------------");
                                }
                            }
                        });

                        ProxiedPlayer player = ProxySystem.getBungeeCord().getPlayer(UUID.fromString(MutePlayer.getUuid()));
                        if(player != null && player.isConnected()) {
                            player.sendMessage("§8§m-----------------------------------------");
                            player.sendMessage(ProxySystem.getMutePrefix() + "§7Du wurdest gemutet§8!");
                            player.sendMessage(ProxySystem.getMutePrefix() + "§7Von§8: §e" + von);
                            player.sendMessage(ProxySystem.getMutePrefix() + "§7Grund§8: §e" + reason);
                            player.sendMessage(ProxySystem.getMutePrefix() + "§7Gemutet bis§8: §e" + getDate(end));
                            player.sendMessage(ProxySystem.getMutePrefix() + "§7BanPoints§8: §e" + MutePoints);
                            player.sendMessage("§8§m-----------------------------------------");
                        }

                    });
                } else {
                    ProxySystem.getBungeeCord().getPlayer(von).sendMessage(ProxySystem.getMutePrefix()+"§cDieser Spieler ist bereits §egeMutet§8!");
                    ProxySystem.getBungeeCord().getPlayer(von).sendMessage(ProxySystem.getMutePrefix()+"§7Für eine Info§8: §e§l/BanInfo <Player>");
                }
                BanPlayerManager.banPlayerHashMap.put(MutePlayer.getUuid(), MutePlayer);
            });
        });



    }

    public void unMutePlayer(BanPlayer MutePlayer, String von, String reason) {
        MutePlayer.setMuteEnd(-1);
        MutePlayer.setMuteFrom("NONE");
        MutePlayer.setMuteReason("NONE");
        ProxySystem.getBanPlayerManager().updateUser(MutePlayer, banPlayer1 -> {
            UUIDFetcher.getName(UUID.fromString(MutePlayer.getUuid()), s -> {
                for (ProxiedPlayer p : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (p.hasPermission("Mute.notify")) {
                        p.sendMessage("§8§m-----------------------------------------");
                        p.sendMessage(ProxySystem.getMutePrefix() + "§7Der Spieler §c" + s + " §7wurde §aentmutet§8!");
                        p.sendMessage(ProxySystem.getMutePrefix() + "§7Von§8: §e" + von);
                        p.sendMessage(ProxySystem.getMutePrefix() + "§7Grund§8: §e" + reason);
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

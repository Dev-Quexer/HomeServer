package me.quexer.homeserver.lobby.dailyreward;

import me.quexer.homeserver.lobby.Lobby;
import me.quexer.homeserver.lobby.entitys.LobbyPlayer;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import me.quexer.homeserver.serverapi.utils.enums.SoundType;
import me.quexer.homeserver.serverapi.utils.manager.EventManager;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyRewardEvents {

    public DailyRewardEvents() {
        ServerAPI.getEventManager().registerEvent(PlayerArmorStandManipulateEvent.class, (EventManager.EventListener<PlayerArmorStandManipulateEvent>) (PlayerArmorStandManipulateEvent event) -> {
            event.setCancelled(true);
            if (event.getRightClicked() == null) {
                return;
            }
                if (event.getRightClicked().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE) {
                    User user = (User) event.getPlayer().getMetadata("user").get(0).value();
                    if(System.currentTimeMillis() >= user.getNextDailyReward()) {
                        user.setNextDailyReward(System.currentTimeMillis()+1000*60*60*24+180000);
                        if(event.getPlayer().hasPermission("premium")) {
                            event.getPlayer().sendMessage("");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8§m==============================");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"    §6Premium §7Daily Reward");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8+ §e3000 §7Coins");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8+ §e2 §7Keys");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8§m==============================");
                            event.getPlayer().sendMessage("");
                            user.setCoins(user.getCoins()+3000);
                            user.setKeys(user.getKeys()+2);
                            ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.GOOD);
                        } else {
                            event.getPlayer().sendMessage("");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8§m==============================");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"    §aSpieler §7Daily Reward");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8+ §e1000 §7Coins");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8+ §e1 §7Key");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§8§m==============================");
                            event.getPlayer().sendMessage(Lobby.getPrefix()+"§7Mit dem §6Premium §7Rang bekommst du eine höhere §e§lBelohung");
                            event.getPlayer().sendMessage("");
                            user.setCoins(user.getCoins()+1000);
                            user.setKeys(user.getKeys()+1);
                            ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.GOOD);
                        }
                        Lobby.getScoreboardManager().getScoreboard(event.getPlayer()).getTeam("keys").setSuffix(user.getKeys()+"");
                        Lobby.getScoreboardManager().getScoreboard(event.getPlayer()).getTeam("coins").setSuffix(user.getCoins()+"");
                    } else {
                        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
                        Date now = new Date();
                        now.setTime(user.getNextDailyReward());
                        ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.BAD);
                        event.getPlayer().sendMessage(Lobby.getPrefix()+"§7Komm Morgen wieder§8.");
                        event.getPlayer().sendMessage(Lobby.getPrefix()+"§e"+sdfDate.format(now));
                    }
                }
        });
    }
}

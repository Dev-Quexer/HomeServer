package me.quexer.homeserver.lobby.chestroll;

import me.quexer.homeserver.lobby.Lobby;
import me.quexer.homeserver.lobby.entitys.LobbyPlayer;
import me.quexer.homeserver.lobby.enums.ContentType;
import me.quexer.homeserver.lobby.enums.Seltenheit;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.quickyapi.utils.ItemBuilder;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import me.quexer.homeserver.serverapi.utils.enums.SoundType;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChestRoll {

    private Player player;
    private int high = 60;
    private BukkitTask task;
    private Seltenheit seltenheit;
    private Random rdm = new Random();
    private ContentType win;
    private User user;
    private LobbyPlayer lobbyPlayer;

    public ChestRoll(Player player, User user) {
        this.player = player;
        this.user = user;
    }

    public void start() {
        int rarity = rdm.nextInt(10000);
        //<editor-fold desc="Seltenheit">
        if(rarity <= 10000 && rarity > 9990) {
            setSeltenheit(Seltenheit.LEGENDARY);
        } else if(rarity <= 9990 && rarity > 9800) {
            setSeltenheit(Seltenheit.ULTIMATE);
        } else if(rarity <= 9800 && rarity > 8000) {
            setSeltenheit(Seltenheit.EPIC);
        } else if(rarity <= 8000 && rarity > 5500) {
            setSeltenheit(Seltenheit.RARE);
        } else if(rarity <= 5500 && rarity > 0) {
            setSeltenheit(Seltenheit.COMMON);
        }
        //</editor-fold>
            setWin(getRandomContent(getSeltenheit()));

            Inventory inv = Bukkit.createInventory(null, 27, "§8➜ §6Chest");
            for (int i = 0; i < inv.getSize(); i++) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).toItemStack());
                inv.setItem(4, new ItemBuilder(Material.HOPPER).setName("§8§l⬇ §7Dein §e§lGewinn §8§l⬇").toItemStack());
            }

            if(getWin().getName().contains("Coins")) {
                user.setCoins(user.getCoins()+getWin().getPrice());
            } else if(getWin().getName().contains("Pass")) {
                if(getWin() == ContentType.REDTONE_ORE) {
                    user.setTraitorPässe(user.getTraitorPässe() + 1);
                } else if(getWin() == ContentType.LAPIS_ORE) {
                    user.setDetectivePässe(user.getDetectivePässe()+1);
                }
            }

            getPlayer().openInventory(inv);


            task = Bukkit.getScheduler().runTaskTimerAsynchronously(Lobby.getInstance(), () -> {

                if (high == 0) {
                    Lobby.getScoreboardManager().getScoreboard(getPlayer()).getTeam("keys").setSuffix(user.getKeys()+"");
                    Lobby.getScoreboardManager().getScoreboard(getPlayer()).getTeam("coins").setSuffix(user.getCoins()+"");
                    inv.setItem(13, getWin().getItemStack());
                    ServerAPI.getUserManager().addXp(user, new Random().nextInt(500)+300);
                    ServerAPI.getSoundManager().play(getPlayer(), SoundType.GOOD);
                    Bukkit.getOnlinePlayers().forEach(o -> {
                        o.sendMessage("");
                        o.sendMessage(Lobby.getPrefix()+"§8§m==============================");
                        o.sendMessage(Lobby.getPrefix()+"§7Gewinn§8: "+getWin().getName());
                        o.sendMessage(Lobby.getPrefix()+"§7Gewonnen von§8: "+player.getDisplayName());
                        o.sendMessage(Lobby.getPrefix()+"§7Seltenheit§8: "+getWin().getRarity().getName());
                        o.sendMessage(Lobby.getPrefix()+"§7Wert§8: §e"+getWin().getPrice()+" §7Coins");
                        o.sendMessage(Lobby.getPrefix()+"§8§m==============================");
                        o.sendMessage("");
                    });
                    Lobby.getPlayerManager().getInChestRoll().remove(getPlayer());
                    task.cancel();
                } else {

                    inv.setItem(17, inv.getItem(16));
                    inv.setItem(16, inv.getItem(15));
                    inv.setItem(15, inv.getItem(14));
                    inv.setItem(14, inv.getItem(13));
                    inv.setItem(13, inv.getItem(12));
                    inv.setItem(12, inv.getItem(11));
                    inv.setItem(11, inv.getItem(10));
                    inv.setItem(10, inv.getItem(9));
                    inv.setItem(9, ContentType.values()[new Random().nextInt(ContentType.values().length)].getItemStack());



                    ServerAPI.getSoundManager().play(getPlayer(), SoundType.NORMAL);
                    high--;
                }

            }, 0, 1);



    }

    public ContentType getRandomContent(Seltenheit seltenheit) {
        List<ContentType> contentTypes = new ArrayList<>();
        for (ContentType contentType : ContentType.values()) {
            if(contentType.getRarity() == seltenheit) {
                contentTypes.add(contentType);
            }
        }
        return contentTypes.get(new Random().nextInt(contentTypes.size()));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ContentType getWin() {
        return win;
    }

    public void setWin(ContentType win) {
        this.win = win;
    }

    public Seltenheit getSeltenheit() {
        return seltenheit;
    }

    public void setSeltenheit(Seltenheit seltenheit) {
        this.seltenheit = seltenheit;
    }

    public Random getRdm() {
        return rdm;
    }

    public void setRdm(Random rdm) {
        this.rdm = rdm;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }
}

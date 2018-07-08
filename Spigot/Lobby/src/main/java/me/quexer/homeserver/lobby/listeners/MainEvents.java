package me.quexer.homeserver.lobby.listeners;

import de.dytanic.cloudnet.api.CloudAPI;
import me.quexer.homeserver.lobby.Lobby;
import me.quexer.homeserver.lobby.entitys.LobbyPlayer;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.InventoryBuilder;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.quickyapi.utils.ItemBuilder;
import me.quexer.homeserver.serverapi.quickyapi.utils.TitleBuilder;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import me.quexer.homeserver.serverapi.utils.enums.SoundType;
import me.quexer.homeserver.serverapi.utils.manager.EventManager;
import me.quexer.homeserver.serverapi.utils.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class MainEvents {

    private List<Player> playerHider = new ArrayList<>();
    private List<Player> used = new ArrayList<>();
    private List<Player> usednick = new ArrayList<>();
    private List<Player> usedprofil = new ArrayList<>();

    public MainEvents() {
        ServerAPI.getEventManager().registerEvent(PlayerJoinEvent.class, (EventManager.EventListener<PlayerJoinEvent>) (PlayerJoinEvent event) -> {
            event.setJoinMessage(null);
            event.getPlayer().getInventory().clear();
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 20, false, false), true);
            Lobby.getLobbyPlayerManager().getLobbyPlayer(event.getPlayer(), lobbyPlayer -> {
                playerHider.forEach(player -> {
                    player.hidePlayer(event.getPlayer());
                });
                if (lobbyPlayer.getLastX() == 0 && lobbyPlayer.getLastY() == 0 && lobbyPlayer.getLastZ() == 0) {
                    event.getPlayer().teleport(Lobby.getLocationManager().getSpawn());
                } else {
                    Location location = new Location(Bukkit.getWorld("world"), lobbyPlayer.getLastX(), lobbyPlayer.getLastY(), lobbyPlayer.getLastZ());
                }

                HashMap<String, Integer> hashMap = new HashMap<>();
                event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Scoreboard wird geladen§8...");
                Bukkit.getScheduler().runTaskLaterAsynchronously(ServerAPI.getInstance(), () -> {
                    ServerAPI.getUserManager().getUser(Bukkit.getOfflinePlayer(event.getPlayer().getUniqueId()), user -> {
                        if(System.currentTimeMillis() >= user.getNextDailyReward()) {
                            TitleBuilder.sendTitle(event.getPlayer(), 0, 60, 60, "§8§l+ §e§lDaily Reward", "§a");
                        }
                        hashMap.put("&a", 16);
                        hashMap.put("&7Server&8: ", 15);
                        hashMap.put("&a&8➜ &e", 14);
                        hashMap.put("&b", 13);
                        hashMap.put("&7Rang&8: ", 12);
                        hashMap.put("&b&8➜ &7" + event.getPlayer().getDisplayName().replace(event.getPlayer().getName(), "").replace(" §8●", ""), 11);
                        hashMap.put("&c", 10);
                        hashMap.put("&7Coins&8: ", 9);
                        hashMap.put("&c&8➜ &e", 8);
                        hashMap.put("&d", 7);
                        hashMap.put("&7Keys&8: ", 6);
                        hashMap.put("&d&8➜ &e", 5);
                        hashMap.put("&e", 4);
                        hashMap.put("&7Elo&8: ", 3);
                        hashMap.put("&e&8➜ &e", 2);
                        hashMap.put("&f", 1);
                        Lobby.getScoreboardManager().setBoard(event.getPlayer(), "&lHOMESERVER.NET", hashMap);


                        Lobby.getScoreboardManager().createTeam(event.getPlayer(), "server", "&a&8➜ &e", "", CloudAPI.getInstance().getOnlinePlayer(UUID.fromString(user.getUuid())).getServer());
                        Lobby.getScoreboardManager().createTeam(event.getPlayer(), "coins", "&c&8➜ &e", "", user.getCoins() + "");
                        Lobby.getScoreboardManager().createTeam(event.getPlayer(), "keys", "&d&8➜ &e", "", user.getKeys() + "");
                        Lobby.getScoreboardManager().createTeam(event.getPlayer(), "elo", "&e&8➜ &e", "", user.getElo() + "");

                    });
                    ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                    ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.GOOD);

                    event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Scoreboard wurde §aerfolgreich §7geladen§8...");
                    Lobby.getPlayerManager().setMainInv(event.getPlayer());
                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                }, 20);

            });
        });
        ServerAPI.getEventManager().registerEvent(PlayerInteractEvent.class, (EventManager.EventListener<PlayerInteractEvent>) (PlayerInteractEvent event) -> {
            new AsyncTask(() -> {
                if (event.getPlayer().getItemInHand() == null) {
                    return;
                }
                if (!event.getPlayer().getItemInHand().hasItemMeta()) {
                    return;
                }
                //<editor-fold desc="Navigator">
                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§e§lNavigator §8┃ §7Rechtsklick") {
                    event.getPlayer().openInventory(Lobby.getPlayerManager().getCompass());
                    ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                    return;
                }
                //</editor-fold>
                //<editor-fold desc="PlayerHider">
                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§e§lSpieler verstecken §8➜ §aAktiviert §8┃ §7Rechtsklick") {
                    if (!used.contains(event.getPlayer())) {
                        event.getPlayer().getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§e§lSpieler verstecken §8➜ §cDeaktiviert §8┃ §7Rechtsklick").toItemStack());
                        Bukkit.getOnlinePlayers().forEach(o -> {
                            event.getPlayer().showPlayer(o);
                        });
                        playerHider.remove(event.getPlayer());
                        event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Alle Spieler sind wieder §asichtbar");
                        LobbyPlayer lobbyPlayer = (LobbyPlayer) event.getPlayer().getMetadata("lobby").get(0).value();
                        lobbyPlayer.setPlayerHider(false);
                        used.add(event.getPlayer());
                        ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                            used.remove(event.getPlayer());
                        }, 60);
                    } else {
                        event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Warte einen §cMoment§8, §7bevor du dieses item benutzt§8.");
                    }
                    return;
                }

                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§e§lSpieler verstecken §8➜ §cDeaktiviert §8┃ §7Rechtsklick") {
                    if (!used.contains(event.getPlayer())) {
                        event.getPlayer().getInventory().setItem(1, new ItemBuilder(Material.STICK).setName("§e§lSpieler verstecken §8➜ §aAktiviert §8┃ §7Rechtsklick").toItemStack());
                        Bukkit.getOnlinePlayers().forEach(o -> {
                            event.getPlayer().hidePlayer(o);
                        });
                        event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Alle Spieler sind §cversteckt");
                        LobbyPlayer lobbyPlayer = (LobbyPlayer) event.getPlayer().getMetadata("lobby").get(0).value();
                        lobbyPlayer.setPlayerHider(true);
                        playerHider.add(event.getPlayer());
                        used.add(event.getPlayer());
                        ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                            used.remove(event.getPlayer());
                        }, 60);
                    } else {
                        event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Warte einen §cMoment§8, §7bevor du dieses item benutzt§8.");
                    }
                    return;
                }
                //</editor-fold>
                //<editor-fold desc="NickTool">
                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lNickTool §8➜")) {
                    if (!usednick.contains(event.getPlayer())) {
                        User user = (User) event.getPlayer().getMetadata("user").get(0).value();
                        if (user.isNick()) {
                            user.setNick(false);
                            event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Du hast das §eNickTool§8: §cDeaktiviert§8.");
                            event.getPlayer().getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG).setName("§e§lNickTool §8➜ §cDeaktiviert §8┃ §7Rechtsklick").toItemStack());
                            usednick.add(event.getPlayer());
                            ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                            Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                                usednick.remove(event.getPlayer());
                            }, 60);
                        } else {
                            user.setNick(true);
                            event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Du hast das §eNickTool§8: §aAktiviert§8.");
                            event.getPlayer().getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG).setName("§e§lNickTool §8➜ §aAktiviert §8┃ §7Rechtsklick").toItemStack());
                            usednick.add(event.getPlayer());
                            ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                            Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                                usednick.remove(event.getPlayer());
                            }, 60);
                        }
                    } else {
                        event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Warte einen §cMoment§8, §7bevor du dieses item benutzt§8.");
                    }
                    return;
                }
                //</editor-fold>
                //<editor-fold desc="LobbySwitcher">
                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§e§lLobbySwitcher §8┃ §7Rechtsklick") {
                    event.getPlayer().openInventory(Lobby.getPlayerManager().getLobbySwitcher());
                    ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                    return;
                }
                //</editor-fold>
                //<editor-fold desc="Freunde">
                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§e§lProfil §8┃ §7Rechtsklick") {
                    if (!usedprofil.contains(event.getPlayer())) {
                        InventoryBuilder builder = new InventoryBuilder("§8➜ §e§lProfil", 27);
                        new AsyncTask(() -> {
                            for (int i = 0; i < builder.build().getSize(); i++) {
                                builder.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName(" ").toItemStack());
                            }
                            User user = (User) event.getPlayer().getMetadata("user").get(0).value();
                            builder.setItem(10, new ItemBuilder(Material.CHEST).setName("§8● §e§lGameinfo").setLore("§a", "§7Elo §8➜ §e§l"+user.getElo(),
                                    "§cTraitor §7Pässe §8➜ §e§l"+user.getTraitorPässe(),
                                    "§9Detective §7Pässe §8➜ §e§l" +user.getDetectivePässe()).toItemStack());
                            builder.setItem(13, new ItemBuilder(Material.BOOK).
                                    setName("§8● §a§lPlayerinfo").setLore("§a", "§7Level §8➜ §e§l"+user.getLevel(),
                                    "§7Xp §8➜ §e§l"+user.getXp()+" §8/ §e§l5000").toItemStack());
                            builder.setItem(16, new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner(event.getPlayer().getName()).
                                    setName("§8● §6§lFreunde").setLore("§a", "§c§lComming Soon...").toItemStack());
                            builder.addListener((player, inv, clickType, itemStack) -> {
                                if(itemStack.getItemMeta().getDisplayName().contains("Freunde")) {
                                    ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.BAD);
                                }
                            });


                            usedprofil.add(event.getPlayer());
                            Bukkit.getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), () -> {
                                usedprofil.remove(event.getPlayer());
                            }, 60);
                        });
                        event.getPlayer().openInventory(builder.build());
                    }
                    ServerAPI.getSoundManager().play(event.getPlayer(), SoundType.NORMAL);
                    return;
                }
                //</editor-fold>
            });
        });
        ServerAPI.getEventManager().registerEvent(PlayerQuitEvent.class, (EventManager.EventListener<PlayerQuitEvent>) (PlayerQuitEvent event) -> {
            event.setQuitMessage(null);
            new AsyncTask(() -> {
                LobbyPlayer lobbyPlayer = (LobbyPlayer) event.getPlayer().getMetadata("lobby").get(0).value();
                lobbyPlayer.setLastX(event.getPlayer().getLocation().getX());
                lobbyPlayer.setLastY(event.getPlayer().getLocation().getY());
                lobbyPlayer.setLastZ(event.getPlayer().getLocation().getZ());
                Lobby.getLobbyPlayerManager().updateLobbyPlayer(lobbyPlayer, lobbyPlayer1 -> {
                    System.out.println("Saved User " + lobbyPlayer.getUuid());
                });
                event.getPlayer().removeMetadata("scoreboard", Lobby.getInstance());
                ServerAPI.getInstance().removeMetadata(event.getPlayer(), "lobby");
            });
        });
    }
}

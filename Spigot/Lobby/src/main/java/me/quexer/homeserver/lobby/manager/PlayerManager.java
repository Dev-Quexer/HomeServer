package me.quexer.homeserver.lobby.manager;

import me.quexer.homeserver.lobby.Lobby;
import me.quexer.homeserver.lobby.entitys.LobbyPlayer;
import me.quexer.homeserver.serverapi.quickyapi.api.InventoryBuilder;
import me.quexer.homeserver.serverapi.quickyapi.utils.ItemBuilder;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private Inventory compass;
    private Inventory lobbySwitcher;
    private List<Player> inChestRoll = new ArrayList<>();

    public PlayerManager() {
        initInventorys();
    }

    public void initInventorys() {
        InventoryBuilder compass = new InventoryBuilder("§8➜ §a§lNavigator", 27);
        for (int i = 0; i < compass.build().getSize(); i++) {
            compass.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName(" ").toItemStack());
        }
        compass.setItem(4, new ItemBuilder(Material.MAGMA_CREAM).setName("§8● §9Spawn").toItemStack());
        compass.setItem(10, new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner("Quexer").setName("§8● §cHomeunity").toItemStack());
        compass.setItem(16, new ItemBuilder(Material.EMERALD).setName("§8● §aDaily§8-§aReward").toItemStack());
        compass.setItem(21, new ItemBuilder(Material.IRON_SWORD).setName("§8● §eQSG").toItemStack());
        compass.setItem(23, new ItemBuilder(Material.CHEST).setName("§8● §4TTT").toItemStack());
        compass.addListener((player, inv, clickType, itemStack) -> {
           if(itemStack.getType() != Material.STAINED_GLASS_PANE) {
               switch (itemStack.getType()) {
                   case MAGMA_CREAM:
                       player.teleport(Lobby.getLocationManager().getSpawn());
                       break;
                   case SKULL_ITEM:
                       player.teleport(Lobby.getLocationManager().getHomeunity());
                       break;
                   case EMERALD:
                       player.teleport(Lobby.getLocationManager().getDaily());
                       break;
                   case IRON_SWORD:
                       player.teleport(Lobby.getLocationManager().getQsg());
                       break;
                   case CHEST:
                       player.teleport(Lobby.getLocationManager().getTtt());
                       break;
               }
           }
        });
        setCompass(compass.build());

        InventoryBuilder lobbyswitcher = new InventoryBuilder("§8➜ §a§lLobbySwitcher", 27);
        for (int i = 0; i < lobbyswitcher.build().getSize(); i++) {
            lobbyswitcher.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName(" ").toItemStack());
        }
        lobbyswitcher.setItem(13, new ItemBuilder(Material.MAGMA_CREAM).setName("§e§7Zum §e§lLobbySwitcher §7teleportieren").toItemStack());
        lobbyswitcher.addListener((player, inv, clickType, itemStack) -> {
           if(itemStack.getType() == Material.MAGMA_CREAM) {
                player.teleport(Lobby.getLocationManager().getLobbyswitcher());
           }
        });
        setLobbySwitcher(lobbyswitcher.build());

    }
    public void setMainInv(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setLevel(2018);
        player.setExp(0);
        User user = (User) player.getMetadata("user").get(0).value();
        LobbyPlayer lobbyPlayer = (LobbyPlayer) player.getMetadata("lobby").get(0).value();
        player.getInventory().setItem(0, new ItemBuilder(Material.DOUBLE_PLANT).setName("§e§lNavigator §8┃ §7Rechtsklick").toItemStack());
        if(lobbyPlayer.isPlayerHider()) {
            player.getInventory().setItem(1, new ItemBuilder(Material.STICK).setName("§e§lSpieler verstecken §8➜ §aAktiviert §8┃ §7Rechtsklick").toItemStack());
        } else {
            player.getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§e§lSpieler verstecken §8➜ §cDeaktiviert §8┃ §7Rechtsklick").toItemStack());
        }
        if(player.hasPermission("nick.nick")) {
            if(user.isNick()) {
                player.getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG).setName("§e§lNickTool §8➜ §aAktiviert §8┃ §7Rechtsklick").toItemStack());
            } else {
                player.getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG).setName("§e§lNickTool §8➜ §cDeaktiviert §8┃ §7Rechtsklick").toItemStack());
            }
        }
        player.getInventory().setItem(7, new ItemBuilder(Material.MAGMA_CREAM).setName("§e§lLobbySwitcher §8┃ §7Rechtsklick").toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner(player.getName()).setName("§e§lProfil §8┃ §7Rechtsklick").toItemStack());

    }

    public List<Player> getInChestRoll() {
        return inChestRoll;
    }

    public void setInChestRoll(List<Player> inChestRoll) {
        this.inChestRoll = inChestRoll;
    }

    public Inventory getCompass() {
        return compass;
    }

    public void setCompass(Inventory compass) {
        this.compass = compass;
    }

    public Inventory getLobbySwitcher() {
        return lobbySwitcher;
    }

    public void setLobbySwitcher(Inventory lobbySwitcher) {
        this.lobbySwitcher = lobbySwitcher;
    }
}

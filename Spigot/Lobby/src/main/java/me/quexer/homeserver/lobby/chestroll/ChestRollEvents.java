package me.quexer.homeserver.lobby.chestroll;

import javafx.scene.control.SliderBuilder;
import me.quexer.homeserver.lobby.Lobby;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.InventoryBuilder;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.quickyapi.utils.ItemBuilder;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import me.quexer.homeserver.serverapi.utils.manager.EventManager;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class ChestRollEvents {


    public ChestRollEvents() {
        ServerAPI.getEventManager().registerEvent(PlayerInteractEvent.class, (EventManager.EventListener<PlayerInteractEvent>) (PlayerInteractEvent event) -> {

            if(event.getClickedBlock() != null) {
                if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                    if (event.getClickedBlock().hasMetadata("chestroll")) {
                        InventoryBuilder inventoryBuilder = new InventoryBuilder("§8➜ §e§lChestRoll §8┃ §7Inv", 54);
                        new AsyncTask(() -> {
                        User user = (User) event.getPlayer().getMetadata("user").get(0).value();
                        long keys = user.getKeys();

                        for (int i = 0; i < inventoryBuilder.build().getSize(); i++) {
                            inventoryBuilder.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDurability((short) 15).toItemStack());
                        }

                        for (int i = 0; i < keys && i < 54; i++) {
                            inventoryBuilder.setItem(i, new ItemBuilder(Material.CHEST).setName("§7Deine Keys§8: §e§l" + keys).setLore("§a", "§7Klicke §7um eine §eKiste zu öffnen").toItemStack());
                        }
                        inventoryBuilder.addListener((player, inv, clickType, itemStack) -> {
                            if (itemStack.getType() == Material.CHEST) {
                                if (!Lobby.getPlayerManager().getInChestRoll().contains(event.getPlayer())) {
                                    Lobby.getPlayerManager().getInChestRoll().add(event.getPlayer());
                                    user.setKeys(user.getKeys()-1);
                                    ChestRoll chestRoll = new ChestRoll(player, (User) event.getPlayer().getMetadata("user").get(0).value());
                                    chestRoll.start();
                                } else {
                                    event.getPlayer().sendMessage(Lobby.getPrefix() + "§7Du öffnest bereits eine §cKiste§8.");
                                }
                            }
                        });
                        });
                        event.setCancelled(true);

                        event.getPlayer().openInventory(inventoryBuilder.build());
                    }
                }
            }

        });
        ServerAPI.getEventManager().registerEvent(InventoryClickEvent.class, (EventManager.EventListener<InventoryClickEvent>) (InventoryClickEvent event) -> {
           if(event.getInventory() == null) {
               return;
           }
            if(event.getClickedInventory() == null) {
                return;
            }
           if(event.getCurrentItem() == null) {
               return;
           }

           if(event.getInventory().getName() == "§8➜ §6Chest") {
               event.setCancelled(true);
           }

        });
    }
}

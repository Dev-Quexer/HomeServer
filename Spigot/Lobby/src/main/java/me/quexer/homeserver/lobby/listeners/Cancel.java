package me.quexer.homeserver.lobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Cancel implements Listener {

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {

        e.setCancelled(true);

    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);

    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        if(!e.getPlayer().hasPermission("build")) {
            e.setCancelled(true);
            e.setBuild(false);
        }

    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(!e.getPlayer().hasPermission("build")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        e.setCancelled(true);
        e.setDamage(0);

    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

}

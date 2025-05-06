package de.michaelmawick.starlobby.listener;

import de.michaelmawick.starlobby.StarLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ProtectionListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (StarLobby.getInstance().getConfig().getBoolean("disable-block-breaking") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }

    }
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (StarLobby.getInstance().getConfig().getBoolean("disable-block-placing") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (StarLobby.getInstance().getConfig().getBoolean("disable-block-breaking") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }

    }

    @EventHandler
    public void onDropItems(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (StarLobby.getInstance().getConfig().getBoolean("disable-item-drop") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }

    }
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (StarLobby.getInstance().getConfig().getBoolean("disable-item-pickup") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {

            if (StarLobby.getInstance().getConfig().getBoolean("disable-clicking") == true) {
                event.setCancelled(true);
            } else {
                event.setCancelled(false);
            }
        }

    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (StarLobby.getInstance().getConfig().getBoolean("disable-wheat-interaction") == true) {
                event.setCancelled(true);
            } else {
                event.setCancelled(false);
            }

        }
    }
    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (StarLobby.getInstance().getConfig().getBoolean("disable-loosing-food") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }

    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        if (StarLobby.getInstance().getConfig().getBoolean("disable-swap-offhand") == true) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }
}

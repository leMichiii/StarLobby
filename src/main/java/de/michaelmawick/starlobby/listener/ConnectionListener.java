package de.michaelmawick.starlobby.listener;

import de.michaelmawick.starlobby.StarLobby;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String joinMessage = StarLobby.getInstance().replacePlaceholders(StarLobby.getInstance().getConfig().getString("join-message"));
        joinMessage = joinMessage.replace("%starlobby_playername%", player.getDisplayName());

        event.setJoinMessage(joinMessage);
        player.setGameMode(GameMode.valueOf(String.valueOf(StarLobby.getInstance().getConfig().get("player-gamemode"))));
        player.setFoodLevel(StarLobby.getInstance().getConfig().getInt("food-level"));
        player.setMaxHealth(StarLobby.getInstance().getConfig().getInt("max-health"));
        player.setHealth(StarLobby.getInstance().getConfig().getInt("health"));

        ItemStack warps = new ItemStack(Material.valueOf(String.valueOf(StarLobby.getInstance().getConfig().get("warps.item"))));
        ItemMeta warpsMeta = warps.getItemMeta();
        warpsMeta.setDisplayName(StarLobby.getInstance().getConfig().getString("warps.displayname"));
        warps.setItemMeta(warpsMeta);

        ItemStack infobook = new ItemStack(Material.valueOf(String.valueOf(StarLobby.getInstance().getConfig().get("infos.item"))));
        ItemMeta infobookMeta = infobook.getItemMeta();
        infobookMeta.setDisplayName(StarLobby.getInstance().getConfig().getString("infos.displayname"));
        infobook.setItemMeta(infobookMeta);

        player.getInventory().setItem(StarLobby.getInstance().getConfig().getInt("warps.slot"), warps);
        player.getInventory().setItem(StarLobby.getInstance().getConfig().getInt("infos.slot"), infobook);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String quitMessage = StarLobby.getInstance().replacePlaceholders(StarLobby.getInstance().getConfig().getString("quit-message"));
        quitMessage = quitMessage.replace("%starlobby_playername%", player.getDisplayName());

        event.setQuitMessage(quitMessage);
        player.getInventory().clear();
    }


}

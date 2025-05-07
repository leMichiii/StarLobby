package de.michaelmawick.starlobby.listener;

import de.michaelmawick.starlobby.StarLobby;
import de.michaelmawick.starlobby.scoreboard.FastBoardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        player.setLevel(StarLobby.getInstance().getConfig().getInt("level"));
        player.setExp(0F);

        player.getInventory().clear();
        StarLobby.getInstance().reloadConfig();

        Set<String> keys = StarLobby.getInstance().getConfig().getConfigurationSection("hotbar").getKeys(false);

        for (String key : keys) {
            int slot;
            try {
                slot = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                StarLobby.getInstance().getLogger().warning("Invalid slot: " + key);
                continue;
            }

            String path = "hotbar." + key;

            String materialStr = StarLobby.getInstance().getConfig().getString(path + ".material", "STONE");
            Material material = Material.matchMaterial(materialStr);
            if (material == null) {
                StarLobby.getInstance().getLogger().warning("Invalid item on slot " + key + ": " + materialStr);
                continue;
            }

            String name = ChatColor.translateAlternateColorCodes('&',
                    StarLobby.getInstance().getConfig().getString(path + ".display-name", "Item"));

            List<String> loreRaw = StarLobby.getInstance().getConfig().getStringList(path + ".lore");
            List<String> lore = loreRaw.stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .collect(Collectors.toList());

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(name);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }

            player.getInventory().setItem(slot, item);
        }
        FastBoardManager.createScoreBoard(player);
    }



    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String quitMessage = StarLobby.getInstance().replacePlaceholders(StarLobby.getInstance().getConfig().getString("quit-message"));
        quitMessage = quitMessage.replace("%starlobby_playername%", player.getDisplayName());

        event.setQuitMessage(quitMessage);
        player.getInventory().clear();
        FastBoardManager.handleQuit(player.getUniqueId());
    }


}

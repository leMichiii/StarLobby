package de.michaelmawick.starlobby.commands;

import de.michaelmawick.starlobby.StarLobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarLobbyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("starlobby.command.reload")) {
                String noPermsMessage = StarLobby.getInstance().replacePlaceholders(StarLobby.getInstance().getConfig().getString("no-perms"));
                sender.sendMessage(noPermsMessage);
                return true;
            }

            StarLobby.getInstance().getServer().reload();
            String reloadMessage = StarLobby.getInstance().replacePlaceholders(StarLobby.getInstance().getConfig().getString("reload-message"));
            sender.sendMessage(reloadMessage);
            return true;
        }

        sender.sendMessage("§cUsage: §a/starlobby reload");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if ("reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("starlobby.command.reload")) {
                completions.add("reload");
            }
            return completions;
        }
        return Collections.emptyList();
    }
}


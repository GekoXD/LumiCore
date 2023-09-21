package main.lumicore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LSCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("animation");
            completions.add("reload");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("animation")) {
            completions.add("@a");
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("animation")) {
            completions.add("TOTEM_RESURRECT");
            completions.add("HURT");
            completions.add("TELEPORT_ENDER");
            completions.add("THORNS_HURT");
        }

        return completions;
    }
}

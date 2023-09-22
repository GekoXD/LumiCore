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
            for (String option : new String[]{"animation", "reload", "config"}) {
                if (option.startsWith(args[0].toLowerCase())) {
                    completions.add(option);
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("config")) {
            for (String option : new String[]{"generate-grave", "totem-use", "custom-death", "custom-join-and-leave"}) {
                if (option.startsWith(args[1].toLowerCase())) {
                    completions.add(option);
                }
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("config")) {
            String option = args[1].toLowerCase();
            for (String value : new String[]{"true", "false"}) {
                if (value.startsWith(args[2].toLowerCase()) && (option.equals("generate-grave") || option.equals("totem-use") || option.equals("custom-death") || option.equals("custom-join-and-leave"))) {
                    completions.add(value);
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("animation")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(playerName);
                }
            }
            if ("@a".startsWith(args[1].toLowerCase())) {
                completions.add("@a");
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("animation")) {
            for (String effect : new String[]{"TOTEM_RESURRECT", "HURT", "TELEPORT_ENDER", "THORNS_HURT"}) {
                if (effect.startsWith(args[2].toUpperCase())) {
                    completions.add(effect);
                }
            }
        }

        return completions;
    }
}
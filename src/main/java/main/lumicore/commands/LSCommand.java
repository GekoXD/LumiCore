package main.lumicore.commands;

import main.lumicore.LumiCore;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LSCommand implements CommandExecutor {

    private final LumiCore plugin;

    public LSCommand(LumiCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("luminarycore.admin")) {
            sender.sendMessage("§6§lLumiCore §cYou do not have permission to run this command.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§6§lLumiCore §cIncomplete command. Command list:");
            sender.sendMessage("§6/lc reload");
            sender.sendMessage("§6/lc animation <player/@a> <animation>");
            sender.sendMessage("§6/lc config <config> <true/false>");
            return true;
        }

        if (args[0].equalsIgnoreCase("animation")) {
            if (args.length < 3) {
                sender.sendMessage("§6§lLumiCore §cIncomplete command for animation.");
                return true;
            }

            String targetArg = args[1];
            String effectArg = args[2];

            if (targetArg.equalsIgnoreCase("@a")) {
                sender.sendMessage("§6§lLumiCore §bThe effect was reproduced in the players.");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playEntityEffect(player, effectArg);
                }
            } else {
                Player targetPlayer = Bukkit.getPlayer(targetArg);
                if (targetPlayer != null) {
                    playEntityEffect(targetPlayer, effectArg);
                    sender.sendMessage("§6§lLumiCore §bThe effect was reproduced in " + targetPlayer.getName());
                } else {
                    sender.sendMessage("§6§lLumiCore §cPlayer not found.");
                }
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            // Recargar la configuración
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "§6§lLumiCore §aConfiguration reloaded successfully.");
        } else if (args[0].equalsIgnoreCase("config")) {
            if (args.length == 3) {
                String option = args[1].toLowerCase();
                if (option.equals("generate-grave") || option.equals("totem-use") || option.equals("custom-death") || option.equals("custom-join-and-leave")) {
                    String input = args[2].toLowerCase();
                    if (input.equals("true") || input.equals("false")) {
                        File configFile = new File(plugin.getDataFolder(), "config.yml");
                        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

                        config.set(option, Boolean.parseBoolean(input));

                        try {
                            config.save(configFile);
                            plugin.reloadConfig();
                            sender.sendMessage("§6§lLumiCore §a" + option + " set to " + input + ".");
                        } catch (IOException e) {
                            e.printStackTrace();
                            sender.sendMessage("§6§lLumiCore §cError al guardar la configuración.");
                        }

                        return true;
                    } else {
                        sender.sendMessage("§6§lLumiCore §cInvalid value. Use 'true' or 'false' for " + option + ".");
                    }
                } else {
                    sender.sendMessage("§6§lLumiCore §cInvalid option. Use 'generate-grave', 'totem-use', 'custom-death', or 'custom-join-and-leave'.");
                }
            } else {
                sender.sendMessage("§6§lLumiCore §cIncomplete command for config. Use '/lc config <option> <true/false>'.");
            }
        } else {
            sender.sendMessage("§6§lLumiCore §cIncomplete command. Command list:");
            sender.sendMessage("§6/lc reload");
            sender.sendMessage("§6/lc animation <player/@a> <animation>");
        }

        return true;
    }

    private void playEntityEffect(Player player, String effectName) {
        if (effectName.equalsIgnoreCase("TOTEM_RESURRECT")) {
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
        } else if (effectName.equalsIgnoreCase("HURT")) {
            player.playEffect(EntityEffect.HURT);
        } else if (effectName.equalsIgnoreCase("TELEPORT_ENDER")) {
            player.playEffect(EntityEffect.TELEPORT_ENDER);
        } else if (effectName.equalsIgnoreCase("THORNS_HURT")) {
            player.playEffect(EntityEffect.THORNS_HURT);
        } else {
            player.sendMessage("§6§lLumiCore §cInvalid entity effect.");
        }
    }
}

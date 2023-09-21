package main.lumicore.commands;

import main.lumicore.LumiCore;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LSCommand implements CommandExecutor {

    private final LumiCore plugin;

    public LSCommand(LumiCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("luminarycore.admin")) {
            sender.sendMessage("§6§lLumiCore §cNo tienes permiso para ejecutar este comando.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§6§lLumiCore §cComando incompleto. Lista de comandos:");
            sender.sendMessage("§6/lc reload");
            sender.sendMessage("§6/lc animation <jugador/@a> <efecto>");
            return true;
        }

        if (args[0].equalsIgnoreCase("animation")) {
            if (args.length < 3) {
                sender.sendMessage("§6§lLumiCore §cComando incompleto para animación.");
                return true;
            }

            String targetArg = args[1];
            String effectArg = args[2];

            if (targetArg.equalsIgnoreCase("@a")) {
                sender.sendMessage("§6§lLumiCore §bSe reprodujo el efecto en los jugadores.");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playEntityEffect(player, effectArg);
                }
            } else {
                Player targetPlayer = Bukkit.getPlayer(targetArg);
                if (targetPlayer != null) {
                    playEntityEffect(targetPlayer, effectArg);
                    sender.sendMessage("§6§lLumiCore §bSe reprodujo el efecto en " + targetPlayer.getName());
                } else {
                    sender.sendMessage("§6§lLumiCore §cJugador no encontrado.");
                }
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            // Recargar la configuración
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "§6§lLumiCore §aConfiguración recargada correctamente.");
        } else {
            sender.sendMessage("§6§lLumiCore §cComando incompleto. Lista de comandos:");
            sender.sendMessage("§6/lc reload");
            sender.sendMessage("§6/lc animation <jugador/@a> <efecto>");
        }

        return true;
    }

    private void playEntityEffect(Player player, String effectName) {
        // Tu lógica para reproducir el efecto en base al nombre proporcionado
        if (effectName.equalsIgnoreCase("TOTEM_RESURRECT")) {
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
        } else if (effectName.equalsIgnoreCase("HURT")) {
            player.playEffect(EntityEffect.HURT);
        } else if (effectName.equalsIgnoreCase("TELEPORT_ENDER")) {
            player.playEffect(EntityEffect.TELEPORT_ENDER);
        } else if (effectName.equalsIgnoreCase("THORNS_HURT")) {
            player.playEffect(EntityEffect.THORNS_HURT);
        } else {
            player.sendMessage("§6§lLumiCore §cEfecto de entidad inválido.");
        }
    }
}

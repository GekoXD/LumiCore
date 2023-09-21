package main.lumicore.events;

import main.lumicore.LumiCore;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    private final LumiCore plugin;

    public JoinListener(LumiCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        boolean entradaSalida = this.plugin.getConfig().getBoolean("custom-join-and-leave", true);

        if (!entradaSalida) {
            return;
        }

        // Tomar valores de config.yml
        String joinMessage = this.plugin.getConfig().getString("join-message");
        if (joinMessage != null) {
            joinMessage = PlaceholderAPI.setPlaceholders(e.getPlayer(), joinMessage);
            joinMessage = joinMessage.replace("%player%", e.getPlayer().getDisplayName());
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
            e.setJoinMessage("");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        boolean entradaSalida = this.plugin.getConfig().getBoolean("custom-join-and-leave", true);

        if (!entradaSalida) {
            return;
        }

        // Tomar valores de config.yml
        String leaveMessage = this.plugin.getConfig().getString("leave-message");
        if (leaveMessage != null){
            leaveMessage = PlaceholderAPI.setPlaceholders(e.getPlayer(), leaveMessage);
            leaveMessage = leaveMessage.replace("%player%", e.getPlayer().getDisplayName());
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
            e.setQuitMessage("");
        }
    }
}

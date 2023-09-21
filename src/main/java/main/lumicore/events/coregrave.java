package main.lumicore.events;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import main.lumicore.LumiCore;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class coregrave implements Listener {
    private final LumiCore plugin;

    public coregrave(LumiCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        boolean generarTumba = this.plugin.getConfig().getBoolean("generate-grave", true);

        if (!generarTumba) {
            return;
        }

        Player p = event.getEntity().getPlayer();
        Location location = p.getLocation();
        Block skullBlock = location.clone().add(0.0, 2.0, 0.0).getBlock();
        skullBlock.setType(Material.PLAYER_HEAD);
        BlockState state = skullBlock.getState();
        Skull skull = (Skull) state;
        UUID uuid = p.getUniqueId();
        skull.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(uuid));
        skull.update();

        // Estructura
        location.clone().add(0.0, 1.0, 0.0).getBlock().setType(Material.CRIMSON_FENCE);
        location.clone().add(0.0, 0.0, 0.0).getBlock().setType(Material.SHROOMLIGHT);
        location.clone().add(1.0, 0.0, 0.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(2.0, 0.0, 0.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(0.0, 0.0, 1.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(0.0, 0.0, 2.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-1.0, 0.0, -0.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-2.0, 0.0, -0.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-0.0, 0.0, -1.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-0.0, 0.0, -2.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-0.0, 1.0, -1.0).getBlock().setType(Material.MOSS_CARPET);
        location.clone().add(-1.0, 1.0, -0.0).getBlock().setType(Material.MOSS_CARPET);
        location.clone().add(-1.0, 0.0, -1.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-1.0, 1.0, -1.0).getBlock().setType(Material.CHAIN);
        location.clone().add(1.0, 0.0, -1.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(1.0, 1.0, -1.0).getBlock().setType(Material.MOSS_CARPET);
        location.clone().add(1.0, 0.0, 1.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(1.0, 1.0, 1.0).getBlock().setType(Material.CRACKED_DEEPSLATE_BRICKS);
        location.clone().add(1.0, 2.0, 1.0).getBlock().setType(Material.CRACKED_DEEPSLATE_BRICKS);
        location.clone().add(2.0, 1.0, 0.0).getBlock().setType(Material.CRACKED_DEEPSLATE_BRICKS);
        location.clone().add(-1.0, 0.0, 1.0).getBlock().setType(Material.OBSIDIAN);
        location.clone().add(-1.0, 1.0, 1.0).getBlock().setType(Material.ANCIENT_DEBRIS);
        location.clone().add(-1.0, 2.0, 1.0).getBlock().setType(Material.ANCIENT_DEBRIS);
        location.clone().add(-2.0, 1.0, -0.0).getBlock().setType(Material.ANCIENT_DEBRIS);
        location.clone().add(-2.0, 2.0, -0.0).getBlock().setType(Material.SOUL_TORCH);
        location.clone().add(1.0, 1.0, 0.0).getBlock().setType(Material.LADDER);
        location.clone().add(1.0, 2.0, 0.0).getBlock().setType(Material.LADDER);

        Block signBlock = location.clone().add(0.0, 1.0, -2.0).getBlock();
        signBlock.setType(Material.OAK_SIGN);

        if (signBlock.getState() instanceof Sign) {
            Sign sign = (Sign) signBlock.getState();
            String linea1 = this.plugin.getConfig().getString("line-1");
            String linea2 = this.plugin.getConfig().getString("line-2");
            String linea3 = this.plugin.getConfig().getString("line-3");
            String linea4 = this.plugin.getConfig().getString("line-4");

            // Linea 1 # Linea 0
            if (linea1 != null) {
                linea1 = PlaceholderAPI.setPlaceholders(p, linea1);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);
                linea1 = linea1.replace("%date%", formattedDate);
                linea1 = linea1.replace("%player%", event.getEntity().getName());
            }

                // Linea 2 # Linea 1

            if (linea2 != null) {
                linea2 = PlaceholderAPI.setPlaceholders(p, linea2);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);
                linea2 = linea2.replace("%date%", formattedDate);
                linea2 = linea2.replace("%player%", event.getEntity().getName());
            }

                    // Linea 3 # Linea 2

            if (linea3 != null) {
                linea3 = PlaceholderAPI.setPlaceholders(p, linea3);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);
                linea3 = linea3.replace("%date%", formattedDate);
                linea3 = linea3.replace("%player%", event.getEntity().getName());
            }

                        // Linea 4 # Linea 3

            if (linea4 != null) {
                linea4 = PlaceholderAPI.setPlaceholders(p, linea4);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);
                linea4 = linea4.replace("%date%", formattedDate);
                linea4 = linea4.replace("%player%", event.getEntity().getName());
            }


            sign.setLine(0, ChatColor.translateAlternateColorCodes('&', linea1));
            sign.setLine(1, ChatColor.translateAlternateColorCodes('&', linea2));
            sign.setLine(2, ChatColor.translateAlternateColorCodes('&', linea3));
            sign.setLine(3, ChatColor.translateAlternateColorCodes('&', linea4));

            sign.update();
        }
    }


            @EventHandler
            public void onBlockBreak (BlockBreakEvent event){
                Block block = event.getBlock();
                if (block.getType() == Material.PLAYER_HEAD) {
                    Skull skull = (Skull) block.getState();
                    UUID ownerUUID = skull.getOwningPlayer().getUniqueId();

                    if (!event.getPlayer().getUniqueId().equals(ownerUUID)) {
                        event.setCancelled(false);
                    }
                }
            }
        }




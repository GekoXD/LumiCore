package main.lumicore.events;

import java.util.Iterator;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class coredeath implements Listener {
    private final Plugin plugin;

    public coredeath(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        boolean muerteCustom = this.plugin.getConfig().getBoolean("custom-death", true);

        if (!muerteCustom) {
            return;
        }

        Player player = e.getEntity();
        Location deathLocation = player.getLocation();

        player.setGameMode(GameMode.SPECTATOR);
        player.setHealth(1.0);

        PotionEffect deathsaturationEffect = new PotionEffect(PotionEffectType.SATURATION, 2, 250); // 1 tick de comida instantánea
        player.addPotionEffect(deathsaturationEffect);

        PotionEffect healthEffect = new PotionEffect(PotionEffectType.HEAL, 2, 250); // 1 tick de comida instantánea
        player.addPotionEffect(healthEffect);

        player.setFoodLevel(20);
        player.setExp(0.0F);
        player.setLevel(0);
        player.teleport(deathLocation);
        player.updateInventory();

        Iterator<PotionEffect> potionIterator = player.getActivePotionEffects().iterator();
        while (potionIterator.hasNext()) {
            PotionEffect effect = potionIterator.next();
            player.removePotionEffect(effect.getType());
        }

        // Tomar valores de config.yml
        String muerteMessage = this.plugin.getConfig().getString("chat-actionbar-death");
        if (muerteMessage != null) {
            muerteMessage = PlaceholderAPI.setPlaceholders(player, muerteMessage);
            muerteMessage = muerteMessage.replace("%death_cause%", e.getDeathMessage());
            TextComponent actionMessage = new TextComponent(ChatColor.translateAlternateColorCodes('&', muerteMessage));

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, actionMessage);
            }
        }


            String deathMessage = ChatColor.translateAlternateColorCodes('&', e.getDeathMessage());
            String deathMessage1 = ChatColor.RED + "§b§l" + deathMessage;


        }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            EntityDamageEvent.DamageCause cause = e.getCause();
        }
    }
}


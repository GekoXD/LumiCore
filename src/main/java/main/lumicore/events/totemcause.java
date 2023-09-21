package main.lumicore.events;

import main.lumicore.LumiCore;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;

import java.util.HashMap;
import java.util.Map;

public class totemcause implements Listener {

    private final LumiCore plugin;

    private final Map<Player, String> lastDamageCauses = new HashMap<>();

    public totemcause(LumiCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        boolean totemMensajes = this.plugin.getConfig().getBoolean("totem-use", true);

        if (!totemMensajes) {
            return;
        }

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String damageCause;

            plugin.reloadConfig();  // Asegurarse de que la configuración esté recargada

            if (event instanceof EntityDamageByEntityEvent) {
                Entity damagerEntity = ((EntityDamageByEntityEvent) event).getDamager();

                if (damagerEntity instanceof Arrow) {
                    Arrow arrow = (Arrow) damagerEntity;
                    if (arrow.getShooter() instanceof Entity) {
                        Entity shooter = (Entity) arrow.getShooter();

                        damageCause = getTranslatedEntityName(shooter);
                    } else {
                        damageCause = "Desconocida"; // O alguna cadena predeterminada
                    }
                } else if (damagerEntity instanceof LivingEntity) {
                    damageCause = getTranslatedEntityName(damagerEntity);
                } else {
                    damageCause = getTranslatedCauseName(damagerEntity.getType().name());
                }
            } else {
                damageCause = getTranslatedCauseName(event.getCause().name());
            }

            lastDamageCauses.put(player, damageCause);
        }
    }

    private String getTranslatedCauseName(String causeName) {
        String translatedCause = plugin.getConfig().getString("messages." + causeName, causeName);
        return translatedCause;
    }

    private String getTranslatedEntityName(Entity entity) {
        String entityName;
        if (entity.getCustomName() != null) {
            entityName = ChatColor.stripColor(entity.getCustomName());
        } else {
            entityName = entity.getType().name();
        }

        String translatedName = getTranslatedCauseName(entityName);
        return translatedName;
    }

    @EventHandler
    public void onTotemUse(EntityResurrectEvent event) {
        boolean totemMensajes = this.plugin.getConfig().getBoolean("totem-use", true);

        if (!totemMensajes) {
            return;
        }

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String damageCause = lastDamageCauses.get(player);

            // Conseguir el config.yml
            this.plugin.reloadConfig(); // Asegurarse de que la configuración esté recargada
            String totemmessage = this.plugin.getConfig().getString("totem-message");
            if (totemmessage != null) {
                totemmessage = totemmessage.replace("%player%", event.getEntity().getName());

                if (damageCause != null) {
                    totemmessage = totemmessage.replace("%causa%", damageCause);
                } else {
                    totemmessage = totemmessage.replace("%causa%", "Desconocida"); // O alguna cadena predeterminada
                }

                Entity damager = event.getEntity(); // Obtener la entidad que fue resucitada (el jugador)
                if (damageCause != null && !damageCause.equalsIgnoreCase("Desconocida")) {
                    // Realizar el reemplazo de placeholders solo si el dañador no es un mob renombrado
                    if (!(damager instanceof Player) || ((Player) damager).getDisplayName().equals(damager.getName())) {
                        totemmessage = PlaceholderAPI.setPlaceholders(player, totemmessage);
                    }
                }

                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', totemmessage));
            }
        }
    }
}



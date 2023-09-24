package main.lumicore.events;

import main.lumicore.LumiCore;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        if (event.getEntity() instanceof Player) {
            boolean totemMensajes = this.plugin.getConfig().getBoolean("totem-use", true);

            if (!totemMensajes) {
                return;
            }

            Player player = (Player) event.getEntity();

            // Verificar si el jugador está realmente siendo resucitado por un totem
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            ItemStack offHandItem = player.getInventory().getItemInOffHand();
            ItemStack usedTotem = null;

            if (mainHandItem.getType() == Material.TOTEM_OF_UNDYING) {
                usedTotem = mainHandItem;
            } else if (offHandItem.getType() == Material.TOTEM_OF_UNDYING) {
                usedTotem = offHandItem;
            }

            if (usedTotem == null) {
                return; // El jugador no está usando un totem
            }

            String damageCause = lastDamageCauses.get(player);

            // Obtener el Custom Model Data del ítem de totem
            ItemMeta totemMeta = usedTotem.getItemMeta();
            int customModelData = -1;

            if (totemMeta != null && totemMeta.hasCustomModelData()) {
                customModelData = totemMeta.getCustomModelData();
            }

            String message;
            if (customModelData != -1 && plugin.getConfig().contains("custom-model-totems." + customModelData)) {
                message = plugin.getConfig().getString("custom-model-totems." + customModelData + ".message");
            } else {
                message = plugin.getConfig().getString("totem-message");
            }

            if (message != null) {
                message = message.replace("%player%", player.getName());

                if (damageCause != null) {
                    message = message.replace("%causa%", damageCause);
                } else {
                    message = message.replace("%causa%", "Desconocida"); // O alguna cadena predeterminada
                }

                Entity damager = player; // Obtener la entidad que fue resucitada (el jugador)
                if (damageCause != null && !damageCause.equalsIgnoreCase("Desconocida")) {
                    if (!(damager instanceof Player) || ((Player) damager).getDisplayName().equals(damager.getName())) {
                        message = PlaceholderAPI.setPlaceholders(player, message);
                    }
                }

                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }
}
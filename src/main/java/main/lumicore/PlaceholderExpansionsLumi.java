package main.lumicore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderExpansionsLumi extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "lumicore"; // Cambia esto por el identificador de tu plugin
    }

    @Override
    public String getAuthor() {
        return "GekoXD"; // Cambia esto por tu nombre
    }

    @Override
    public String getVersion() {
        return "1.0.0"; // Cambia esto por la versión de tu plugin
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player == null) {
            return "";
        }

        // Aquí puedes manejar los placeholders y devolver el resultado deseado
        if (params.equals("version")) {
            return "LumiCore Version: 1.0";
        }

        return null; // Devuelve null si el placeholder no está definido
    }
}



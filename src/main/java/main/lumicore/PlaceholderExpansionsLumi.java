package main.lumicore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderExpansionsLumi extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "lumicore";
    }

    @Override
    public String getAuthor() {
        return "GekoXD";
    }

    @Override
    public String getVersion() {
        return "1.0.4";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player == null) {
            return "";
        }

        if (params.equals("version")) {
            return "LumiCore Version: 1.0.4";
        }

        return null; // Devuelve null si el placeholder no está definido
    }
}



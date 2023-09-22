package main.lumicore;

import main.lumicore.commands.LSCommand;
import main.lumicore.commands.LSCommandTabCompleter;
import main.lumicore.events.*;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public final class LumiCore extends JavaPlugin {
    ConsoleCommandSender consolemessage = Bukkit.getConsoleSender();

    PluginDescriptionFile pluginFile = this.getDescription();
    public String version;
    public String pluginName;
    public String latestversion;

    public LumiCore() {
        this.version = this.pluginFile.getVersion();
        this.pluginName = this.pluginFile.getName();
    }


    private static LumiCore instance;
    private static LumiCore plugin;

    @Override
    public void onEnable() {
        plugin = this;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new PlaceholderExpansionsLumi()).register();
        }
        instance = this;
        // Tomar config.yml
        getConfig();
        saveDefaultConfig();

        this.getServer().getPluginManager().registerEvents(new coredeath(this), this);
        getServer().getPluginManager().registerEvents(new totemcause(this), this);
        getServer().getPluginManager().registerEvents(new coregrave(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        getCommand("lc").setExecutor(new LSCommand(this));
        getCommand("lc").setTabCompleter(new LSCommandTabCompleter());
        this.consolemessage.sendMessage("§7---------------------------------------------------------");
        this.consolemessage.sendMessage("§6§lLumiCore §7| §aStarting Plugin");
        this.consolemessage.sendMessage("§7---------------------------------------------------------");
        this.CheckUpdate();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private void loadConfig() {
        this.saveDefaultConfig();
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        this.consolemessage.sendMessage("§7---------------------------------------------------------");
        this.consolemessage.sendMessage("§6§lLumiCore §7| Turning off Plugin");
        this.consolemessage.sendMessage("§7---------------------------------------------------------");
    }

        public void CheckUpdate () {
            try {
                HttpURLConnection con = (HttpURLConnection) (new URL("api.spigotmc.org/legacy/update.php?resource=112734")).openConnection();
                int timed_out = 5000;
                con.setConnectTimeout(timed_out);
                con.setReadTimeout(timed_out);
                this.latestversion = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
                if (this.latestversion.length() <= 7 && !this.version.equals(this.latestversion)) {
                    Bukkit.getConsoleSender().sendMessage("§6§lLumiCore §6There is a new version available. (§fv" + this.latestversion + "§6)");
                    Bukkit.getConsoleSender().sendMessage("§6§lLumiCore §2You can download it at: §7https://www.spigotmc.org/resources/112734/");
                }
            } catch (Exception var3) {
                Bukkit.getConsoleSender().sendMessage("§6§lLumiCore §cError trying to check for updates.");
            }

        }
    }

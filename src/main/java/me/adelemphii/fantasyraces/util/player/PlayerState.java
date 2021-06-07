package me.adelemphii.fantasyraces.util.player;

import me.adelemphii.fantasyraces.FantasyRaces;
import me.adelemphii.fantasyraces.util.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerState {

    FantasyRaces plugin;
    public PlayerState(FantasyRaces plugin) {
        this.plugin = plugin;
    }

    Player player;

    DataManager config = new DataManager(JavaPlugin.getPlugin(FantasyRaces.class));

    public PlayerState(Player player) {
        this.player = player;
    }

    public String getPlayerRace() {
        if(config.getConfig().get("players." + player.getUniqueId() + ".persona.race") == null) {
            return "";
        }
        return config.getConfig().getString("players." + player.getUniqueId() + ".persona.race");
    }

    public void setPlayerRace(String race) {
        config.getConfig().set("players." + player.getUniqueId() + ".persona.race", race);
        config.saveConfig();
    }

    public void setPlayerName(String name) {
        config.getConfig().set("players." + player.getUniqueId() + ".persona.name", name);
        config.saveConfig();
    }

    public String getPlayerName() {
        if(config.getConfig().getString("players." + player.getUniqueId() + ".persona.name") == null) {
            return player.getName();
        }

        return config.getConfig().getString("players." + player.getUniqueId() + ".persona.name");
    }

    public void setPlayerAge(String age) {
        config.getConfig().set("players." + player.getUniqueId() + ".persona.age", age);
        config.saveConfig();
    }

    public String getPlayerAge() {
        return config.getConfig().getString("players." + player.getUniqueId() + ".persona.age");
    }
}

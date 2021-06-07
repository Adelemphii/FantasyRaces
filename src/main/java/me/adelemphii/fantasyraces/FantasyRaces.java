package me.adelemphii.fantasyraces;

import me.adelemphii.fantasyraces.commands.PersonaCommand;
import me.adelemphii.fantasyraces.events.DwarfTossEvents;
import me.adelemphii.fantasyraces.events.JoinEvent;
import me.adelemphii.fantasyraces.util.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class FantasyRaces extends JavaPlugin {

    Map<UUID, Boolean> dwarfToss = new HashMap<>();

    DataManager data;

    @Override
    public void onEnable() {
        data = new DataManager(this);

        saveDefaultConfig();

        registerCE();

    }

    @Override
    public void onDisable() {
    }

    public void registerCE() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new DwarfTossEvents(this), this);

        getCommand("persona").setExecutor(new PersonaCommand());
    }

    public Map<UUID, Boolean> getDwarfToss() {
        return dwarfToss;
    }

    public void setDwarfToss(UUID uuid, boolean state) {
        dwarfToss.put(uuid, state);
    }

}

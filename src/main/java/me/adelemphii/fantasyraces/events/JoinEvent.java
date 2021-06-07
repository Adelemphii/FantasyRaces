package me.adelemphii.fantasyraces.events;

import me.adelemphii.fantasyraces.FantasyRaces;
import me.adelemphii.fantasyraces.util.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    DataManager config = new DataManager(FantasyRaces.getPlugin(FantasyRaces.class));

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if(config.getConfig().getString("players." + player.getUniqueId() + ".persona.name") != null) {
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("players." + player.getUniqueId() + ".persona.name")));
        }

    }

}

package me.adelemphii.fantasyraces.events;

import me.adelemphii.fantasyraces.FantasyRaces;
import me.adelemphii.fantasyraces.util.player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.List;

public class DwarfTossEvents implements Listener {

    FantasyRaces plugin;

    public DwarfTossEvents(FantasyRaces plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent event) {

        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!event.getPlayer().isSneaking()) {
            return;
        }

        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();

        PlayerState playerState = new PlayerState(player);
        PlayerState targetState = new PlayerState(target);

        if (playerState.getPlayerRace().equalsIgnoreCase("dwarf")
                && targetState.getPlayerRace().equalsIgnoreCase("dwarf")) {
            player.addPassenger(target);

            player.sendMessage("You are now tossing " + target.getName());
            target.sendMessage("You are being tossed by " + player.getName());

            plugin.setDwarfToss(target.getUniqueId(), true);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        Player target;

        PlayerState playerState = new PlayerState(player);

        if (!playerState.getPlayerRace().equalsIgnoreCase("dwarf")) {
            return;
        }

        if (player.getPassengers().isEmpty()) {
            return;
        }

        List<Entity> passengers = player.getPassengers();

        for (Entity entity : passengers) {
            if (entity instanceof Player) {
                target = (Player) entity;

                if (plugin.getDwarfToss().get(target.getUniqueId())) {
                    player.eject();

                    target.setVelocity(player.getLocation().getDirection().multiply(2.45f).setY(1.2f));

                    player.sendMessage("You tossed " + target.getName());
                    target.sendMessage("You were tossed by " + player.getName());
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

            if(!plugin.getDwarfToss().containsKey(player.getUniqueId())) { return; }

            if (plugin.getDwarfToss().get(player.getUniqueId())) {
                event.setCancelled(true);
                plugin.setDwarfToss(player.getUniqueId(), false);

                Location landing = player.getLocation();
                World world = landing.getWorld();

                world.playSound(landing, Sound.BLOCK_ANVIL_LAND, 1f, .5f);

                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {

                    Vector vector = entity.getLocation().toVector().subtract(landing.toVector()).setY(1).normalize();

                    entity.setVelocity(vector);

                    if (entity instanceof Player) {
                        Player nearby = (Player) entity;

                        nearby.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lBoom! &c" + player.getName() + " has been tossed into the fray!"));
                    }
                }
            }
        }

        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if(plugin.getDwarfToss().containsKey(player.getUniqueId())) {
                if(plugin.getDwarfToss().get(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

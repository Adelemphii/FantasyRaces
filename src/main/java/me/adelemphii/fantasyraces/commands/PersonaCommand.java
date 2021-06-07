package me.adelemphii.fantasyraces.commands;

import me.adelemphii.fantasyraces.util.player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PersonaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) { return false; }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("persona")) {

            PlayerState playerState = new PlayerState(player);

            if(args.length == 0) {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&8---- &7" + player.getName() + "'s persona &8----\n" +
                                "&7Name: " + playerState.getPlayerName() + "\n" +
                                "&7Race: " + playerState.getPlayerRace() + "\n" +
                                "Age: " + playerState.getPlayerAge() + "\n"));
                return true;
            } else if(args.length >= 2) {

                switch(args[0].toLowerCase()) {
                    case "name":
                        playerState.setPlayerName(args[1]);
                        player.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[1]));
                        player.sendMessage("Name set to " + args[1]);
                        return true;
                    case "race":
                        switch(args[1].toLowerCase()) {
                            case "dwarf":
                            case "human":
                            case "elf":
                                playerState.setPlayerRace(args[1]);
                                player.sendMessage("Race set to " + args[1]);
                                return true;
                            default:
                                player.sendMessage(ChatColor.RED + "That is not a valid race. The valid races are: Dwarf, Elf, Human");
                                return false;
                        }
                    case "age":
                        playerState.setPlayerAge(args[1]);
                        player.sendMessage("Age set to " + args[1]);
                        return true;
                    default:
                        player.sendMessage(ChatColor.RED + "That is not a valid command, try to set your name, race or age.");
                        return false;
                }
            }
        }
        return false;
    }
}

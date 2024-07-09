package dev.octomc.agile.configurable.action.impl;

import dev.octomc.agile.configurable.action.Action;
import org.bukkit.entity.Player;

public class CommandAction extends Action {
    private String command;

    @Override
    public void run(Player player) {
        player.performCommand(command);
    }
}
    

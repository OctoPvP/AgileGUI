package dev.octomc.agile.configurable.condition.impl;

import dev.octomc.agile.configurable.condition.Condition;
import org.bukkit.entity.Player;

public class PermissionCondition extends Condition {
    private String permission;

    @Override
    public boolean conditionIsMet(Player player, Object args) {
        return player.hasPermission(permission);
    }
}

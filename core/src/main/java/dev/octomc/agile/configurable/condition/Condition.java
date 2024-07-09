package dev.octomc.agile.configurable.condition;

import org.bukkit.entity.Player;

public abstract class Condition {
    public abstract boolean conditionIsMet(Player player, Object args);
}
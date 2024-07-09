package dev.octomc.agile.configurable.condition.impl;

import dev.octomc.agile.configurable.condition.Condition;
import org.bukkit.entity.Player;

public class AndCondition extends Condition {
    private Condition a, b;

    @Override
    public boolean conditionIsMet(Player player, Object args) {
        return a.conditionIsMet(player, args) && b.conditionIsMet(player, args);
    }
}

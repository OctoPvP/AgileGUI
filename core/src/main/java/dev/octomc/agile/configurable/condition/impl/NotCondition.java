package dev.octomc.agile.configurable.condition.impl;

import dev.octomc.agile.configurable.condition.Condition;
import org.bukkit.entity.Player;

public class NotCondition extends Condition {
    private Condition condition;

    @Override
    public boolean conditionIsMet(Player player, Object args) {
        return !condition.conditionIsMet(player, args);
    }
}

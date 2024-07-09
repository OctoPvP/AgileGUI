package dev.octomc.agile.configurable;

import dev.octomc.agile.configurable.condition.Condition;
import dev.octomc.agile.menu.Menu;
import lombok.Data;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.Player;

import java.util.List;

@Data
public class MenuConfig {
    private String title;
    private int rows;
    private List<MenuItemConfig> items;

    private FillConfig fill;

    public Gui createGui(ConfigurableMenuManager manager) {
        return Gui.gui()
                .title(manager.getMiniMessage().deserialize(title))
                .rows(rows)
                .disableAllInteractions()
                .create();
    }

    public void populate(Gui gui, Player player, ConfigurableMenuManager manager) {
        for (MenuItemConfig item : items) {
            GuiItem guiItem = item.asGuiItem(player, gui, this, manager);
            gui.setItem(item.getSlot(), guiItem);
        }
        if (fill != null && fill.isEnabled()) {
            gui.getFiller().fill(fill.isUseCustom() ? fill.getItem().asGuiItem(player, gui, this, manager) : Menu.PLACEHOLDER_ITEM);
        }
    }

    public void open(Player player, ConfigurableMenuManager manager) {
        try {
            Gui gui = createGui(manager);
            populate(gui, player, manager);
            gui.open(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class ConditionalItem {
        private MenuItemConfig item;
        private Condition condition;
    }

    @Data
    public static class FillConfig {
        private MenuItemConfig item;
        boolean enabled, useCustom;
    }
}
package dev.octomc.agile.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @deprecated Legacy menu button, will be removed in the future.
 */
@Deprecated
public abstract class Button {
    public abstract void onClick(Player player, int slot, ClickType clickType, InventoryClickEvent event, BaseGui gui);

    public abstract ItemStack getItem(Player player);

    public abstract int getSlot();

    public void set(BaseGui gui, Player player) {
        GuiItem item = ItemBuilder.from(getItem(player)).asGuiItem(event -> {
            onClick(player, event.getSlot(), event.getClick(), event, gui);
        });
        gui.setItem(getSlot(), item);
    }

    public void update(BaseGui gui, Player player) {
        ItemStack item = getItem(player);
        gui.updateItem(getSlot(), item);
    }
}

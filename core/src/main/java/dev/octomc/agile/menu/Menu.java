package dev.octomc.agile.menu;

import com.cryptomorin.xseries.XMaterial;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public abstract class Menu<T extends BaseGui> { // TODO: Make "public guis", which multiple players can view at once
    protected T gui; // gui should not be called until open is called.

    public static GuiItem PLACEHOLDER_ITEM = ItemBuilder.from(Objects.requireNonNull(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()))
            .name(Component.empty()).asGuiItem(e -> {
            });

    public abstract T createGui(Player player);

    public abstract void populateGui(T gui, Player player);

    public void open(Player player) {
        gui = createGui(player);
        updateInteractions();
        populate(gui, player);
        gui.open(player);
    }

    public void update(Player player) {
        clear();
        populate(gui, player);
        gui.update();
    }

    public void clear() {
        gui.clear();
        /*
        Iterator<@NotNull Integer> iterator = gui.getGuiItems().keySet().iterator();
        while (iterator.hasNext()) {
            Integer slot = iterator.next();
            if (slot != null) {
                gui.removeItem(slot);
            }
        }
         */
    }

    public void populate(T gui, Player player) {
        populateGui(gui, player);
        List<Button> legacyButtons = getLegacyButtons(player);
        if (legacyButtons != null) {
            for (Button button : legacyButtons) {
                button.set(gui, player);
            }
        }
    }

    public List<Button> getLegacyButtons(Player player) {
        return null;
    }

    public void updateInteractions() {
        gui.disableAllInteractions();
    }

    public GuiItem closeButton() {
        return ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseItem()))
                .name(Component.text("Close", NamedTextColor.RED))
                .asGuiItem(e -> gui.close(e.getWhoClicked()));
    }

    public GuiItem backButton(Menu<?> previous) {
        return ItemBuilder.from(Objects.requireNonNull(XMaterial.ARROW.parseItem()))
                .name(Component.text("Back", NamedTextColor.YELLOW))
                .asGuiItem(e -> previous.open((Player) e.getWhoClicked()));
    }
}

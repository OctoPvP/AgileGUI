package dev.octomc.agile.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu<T extends PaginatedGui> extends Menu<T> {
    @Override
    public void populateGui(T gui, Player player) {
        if (fillBorders()) {
            gui.getFiller().fillBorder(PLACEHOLDER_ITEM);
        }
        for (GuiItem item : getItems(player)) {
            gui.addItem(item);
        }
        setButtons();
    }

    public abstract List<GuiItem> getItems(Player player);

    public boolean fillBorders() {
        return true;
    }

    public void setButtons() { // TODO: add filter buttons
        int rows = gui.getRows();
        Menu<?> backMenu = getBackMenu();
        gui.updateItem(rows, 1, previousButton());
        gui.updateItem(rows, 9, nextButton());
        gui.updateItem(rows, 5, closeButton());
        if (backMenu != null) {
            gui.updateItem(rows, 4, backButton(backMenu));
        }
        GuiItem filter = getFilterButton();
        if (filter != null) {
            gui.updateItem(rows, 6, filter);
        }
        addStaticButtons();
    }

    public void addStaticButtons() {

    }

    public Menu<?> getBackMenu() {
        return null;
    }

    public GuiItem nextButton() {
        List<Component> components = new ArrayList<>();
        int pageNum = gui.getCurrentPageNum();
        int totalPages = gui.getPagesNum();
        boolean isLastPage = pageNum + 1 > totalPages;
        if (isLastPage) {
            components.add(Component.text("This is the last page!", NamedTextColor.RED));
        } else {
            components.add(Component.text("Click to go to the next page", NamedTextColor.YELLOW));
        }
        return ItemBuilder.from(Material.ARROW)
                .name(Component.text("Next Page", NamedTextColor.GREEN))
                .lore(components).asGuiItem(event -> {
                    gui.next();
                    setButtons();
                });
    }

    public GuiItem previousButton() {
        List<Component> components = new ArrayList<>();
        int pageNum = gui.getCurrentPageNum();
        boolean firstPage = pageNum - 1 == 0;
        if (firstPage) {
            components.add(Component.text("This is the first page!")
                    .color(NamedTextColor.RED));
        } else {
            components.add(Component.text("Click to go to the previous page")
                    .color(NamedTextColor.YELLOW));
        }
        return ItemBuilder.from(Material.ARROW)
                .name(Component.text("Previous Page", NamedTextColor.GREEN))
                .lore(components)
                .asGuiItem(event -> {
                    gui.previous();
                    setButtons();
                });
    }

    public GuiItem getFilterButton() {
        return null;
    }

    public GuiItem getBackButton(Menu<?> menu) {
        return backButton(menu);
    }
}

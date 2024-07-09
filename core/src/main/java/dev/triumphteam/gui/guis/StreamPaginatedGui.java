package dev.triumphteam.gui.guis;

import dev.octomc.agile.util.TriFunction;
import dev.triumphteam.gui.components.InteractionModifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public class StreamPaginatedGui extends PaginatedGui {
    // callbacks for:
    // 1. populate gui (gui, player, page, expected max items) -> List<GuiItem> - throw exception if over max items is provided
    // 2. get number of pages (gui, player, expected max items) -> int
    private Populator populateItems;
    private TriFunction<StreamPaginatedGui, Player, Integer, Integer> pages;
    private Player player;

    public StreamPaginatedGui(int rows, int pageSize, @NotNull String title, @NotNull Set<InteractionModifier> interactionModifiers,
                              Populator populateItems,
                              TriFunction<StreamPaginatedGui, Player, Integer, Integer> pages,
                              Player player
                              ) {
        super(rows, pageSize, title, interactionModifiers);
        this.populateItems = populateItems;
        this.pages = pages;
        this.player = player;
    }

    public int getExpectedMaxItems() {
        int cells = getRows() * 9;
        Map<Integer, GuiItem> staticItems = getGuiItems();
        return cells - staticItems.size();
    }

    @Override
    public List<GuiItem> getPageNum(int givenPage) {
        int max = getExpectedMaxItems();
        List<GuiItem> list = populateItems.populate(this, player, givenPage, max);
        if (list == null) return new ArrayList<>();
        if (list.size() > max) {
            throw new IllegalStateException("StreamPaginatedGui: Too many items provided, max: " + max + ", provided: " + list.size());
        }
        return list;
    }

    @Override
    public int getPagesNum() {
        return pages.apply(this, player, getExpectedMaxItems());
    }

    @Override
    public List<GuiItem> getPageNum(int givenPage, List<GuiItem> pageItems) {
        throw new UnsupportedOperationException("StreamPaginatedGui does not support getPageNum(int, List<GuiItem>)");
    }

    public static interface Populator {
        List<GuiItem> populate(StreamPaginatedGui gui, Player player, int page, int max);
    }
}

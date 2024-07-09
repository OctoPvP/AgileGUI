package dev.triumphteam.gui.guis;

import dev.octomc.agile.util.ReturnCallback;
import dev.triumphteam.gui.components.InteractionModifier;
import lombok.Getter;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Getter
@Deprecated
public class StreamPaginatedGui extends PaginatedGui {
    private ReturnCallback<List<GuiItem>, Integer, StreamPaginatedGui> populateItems;
    private ReturnCallback<Integer, Integer, StreamPaginatedGui> pages; // returns the number of pages, given the current page and the gui

    private Consumer<StreamPaginatedGui> onPopulate;

    public StreamPaginatedGui(int rows, @NotNull String title,
                              @NotNull Set<InteractionModifier> interactionModifiers,
                              ReturnCallback<List<GuiItem>, Integer, StreamPaginatedGui> populateItems,
                              ReturnCallback<Integer, Integer, StreamPaginatedGui> pages,
                              Consumer<StreamPaginatedGui> onPopulate, int pageSize) {
        super(rows, pageSize, title, interactionModifiers);
        this.populateItems = populateItems;
        this.pages = pages;
        this.onPopulate = onPopulate;
    }


    @Override
    public int getPagesNum() {
        //return super.getPagesNum();
        return pages.call(getPageNum(), this);
    }

    @Override
    public List<GuiItem> getPageNum(int givenPage) {
        List<GuiItem> list = populateItems.call(givenPage, this);
        if (list == null) return getPageItems();
        list.addAll(getPageItems());
        return getPageNum(givenPage, list);
    }


    @Override
    public void populatePage() {
        super.populatePage();
        if (onPopulate != null) onPopulate.accept(this);
    }

    @Override
    public void open(@NotNull HumanEntity player, int openPage) {
        if (player.isSleeping()) return;
        if (openPage <= getPagesNum() || openPage > 0) pageNum = openPage;

        getInventory().clear();
        currentPage.clear();

        if (pageSize == 0) pageSize = calculatePageSize();

        populatePage();
        populateGui();

        player.openInventory(getInventory());
    }

    @Override
    void updatePage() {
        clearPage();
        populateGui();
        populatePage();
    }
}

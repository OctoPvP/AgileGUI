/**
 * MIT License
 * <p>
 * Copyright (c) 2021 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder.gui;

import dev.octomc.agile.util.TriFunction;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.guis.PaginatedGui;
import dev.triumphteam.gui.guis.StreamPaginatedGui;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * GUI builder for creating a {@link PaginatedGui}
 */
@AllArgsConstructor
public class StreamPaginatedBuilder extends BaseGuiBuilder<StreamPaginatedGui, StreamPaginatedBuilder> {
    private StreamPaginatedGui.Populator populateItems;
    private TriFunction<StreamPaginatedGui, Player, Integer, Integer> pages;
    private Player player;

    /**
     * Creates a new {@link PaginatedGui}
     *
     * @return A new {@link PaginatedGui}
     */
    @NotNull
    @Override
    @Contract(" -> new")
    public StreamPaginatedGui create() {
        final StreamPaginatedGui gui = new StreamPaginatedGui(getRows(), 0, Legacy.SERIALIZER.serialize(getTitle()), getModifiers(), populateItems, pages, player);

        final Consumer<StreamPaginatedGui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }
}

package dev.octomc.agile.configurable;

import com.google.gson.*;
import dev.octomc.agile.configurable.action.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.kyori.adventure.text.Component;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuItemConfig {
    private MenuItemConfig parent;
    private int slot;
    private String material;
    private String name;
    private String[] lore;
    private List<Action> actions;
    private List<MenuConfig.ConditionalItem> conditionalItems;

    public static MenuItemConfig fromJsonObject(JsonObject object, ConfigurableMenuManager manager) {
        MenuItemConfig menuItemConfig = new MenuItemConfig();
        if (object.has("slot")) {
            menuItemConfig.setSlot(object.get("slot").getAsInt());
        }
        if (object.has("material")) {
            menuItemConfig.setMaterial(object.get("material").getAsString());
        }
        if (object.has("name")) {
            menuItemConfig.setName(object.get("name").getAsString());
        }
        if (object.has("lore")) {
            JsonArray lore = object.get("lore").getAsJsonArray();
            String[] loreArray = new String[lore.size()];
            for (int i = 0; i < lore.size(); i++) {
                loreArray[i] = lore.get(i).getAsString();
            }
            menuItemConfig.setLore(loreArray);
        }
        if (object.has("actions")) {
            JsonArray actions = object.get("actions").getAsJsonArray();
            List<Action> actionList = new ArrayList<>();
            for (JsonElement action : actions) {
                actionList.add(manager.getGson().fromJson(action.getAsJsonObject(), Action.class));
            }
            menuItemConfig.setActions(actionList);
        }
        if (object.has("conditionalItems")) {
            JsonArray conditionalItems = object.get("conditionalItems").getAsJsonArray();
            List<MenuConfig.ConditionalItem> conditionalItemList = new ArrayList<>();
            for (JsonElement conditionalItem : conditionalItems) {
                conditionalItemList.add(manager.getGson().fromJson(conditionalItem.getAsJsonObject(), MenuConfig.ConditionalItem.class));
            }
            menuItemConfig.setConditionalItems(conditionalItemList);
        }
        return menuItemConfig;
    }

    public Material getMaterial() {
        String material = this.material == null ? (parent != null ? parent.material : null) : this.material;
        return Material.getMaterial(material);
    }

    @SuppressWarnings("deprecation")
    public GuiItem asGuiItem(Player player, Gui gui, MenuConfig config, ConfigurableMenuManager manager) {
        try {
            boolean hasParent = parent != null;
            String name = this.name == null ? (hasParent ? parent.name : "") : this.name;
            String[] lore = this.lore == null || this.lore.length == 0 ? (hasParent ? parent.lore : new String[]{}) : this.lore;
            List<Action> actions = getActions();
            List<MenuConfig.ConditionalItem> conditionalItems = getConditionalItems();
            if (conditionalItems != null) {
                //System.out.println("name: " + name + " | lore: " + Arrays.asList(lore) + " | actions: " + actions.size() + " | cond: " + conditionalItems.size());
                for (MenuConfig.ConditionalItem conditionalItem : conditionalItems) {
                    if (conditionalItem.getCondition().conditionIsMet(player, null)) {
                        MenuItemConfig i = conditionalItem.getItem();
                        if (i != this)
                            return conditionalItem.getItem().asGuiItem(player, gui, config, manager);
                    }
                }
            } else {
                //System.out.println("name: " + name + " | lore: " + Arrays.asList(lore) + " | actions: " + actions.size() + " | cond: null");
            }

            List<Component> loreComponents = new ArrayList<>();
            for (String loreLine : lore) {
                loreComponents.add(
                        manager.getMiniMessage().deserialize(
                                loreLine
                        )
                );
            }
            return ItemBuilder.from(getMaterial())
                    .name(manager.getMiniMessage().deserialize(name))
                    .lore(loreComponents)
                    .asGuiItem(event -> {
                        if (actions != null) {
                            for (Action action : actions) {
                                action.run(((Player) event.getWhoClicked()));
                            }
                        }
                        //gui.setItem(event.getSlot(), asGuiItem(player,gui));
                        //gui.update();
                        config.open(player, manager); // TODO: reopening the gui causes flickering, figure out how to update the item.
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Action> getActions() {
        return this.actions == null || this.actions.isEmpty() ? (parent != null ? parent.getActions() : null) : this.actions;
    }

    public List<MenuConfig.ConditionalItem> getConditionalItems() {
        return this.conditionalItems == null || this.conditionalItems.isEmpty() ? (parent != null ? parent.getConditionalItems() : null) : this.conditionalItems;
    }

    @AllArgsConstructor
    public static class Serializer implements JsonSerializer<MenuItemConfig>, JsonDeserializer<MenuItemConfig> {
        private ConfigurableMenuManager manager;
        @Override
        public MenuItemConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            MenuItemConfig config = fromJsonObject(json.getAsJsonObject(), manager); //context.deserialize(json, MenuItemConfig.class);
            if (config.getConditionalItems() != null) {
                for (MenuConfig.ConditionalItem conditionalItem : config.getConditionalItems()) {
                    conditionalItem.getItem().setParent(config);
                }
            }
            return config;
        }

        @Override
        public JsonElement serialize(MenuItemConfig src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = context.serialize(src, typeOfSrc).getAsJsonObject();
            // remove parent from conditional items
            if (src.getConditionalItems() != null && !src.getConditionalItems().isEmpty()) {
                for (JsonElement conditionalItems : object.get("conditionalItems").getAsJsonArray()) {
                    JsonObject conditionalItem = conditionalItems.getAsJsonObject();
                    conditionalItem.remove("parent");
                }
            }
            return object;
        }
    }
}
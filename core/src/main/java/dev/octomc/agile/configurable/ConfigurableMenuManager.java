package dev.octomc.agile.configurable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import dev.octomc.agile.configurable.action.Action;
import dev.octomc.agile.configurable.action.ActionSerializer;
import dev.octomc.agile.configurable.action.impl.CommandAction;
import dev.octomc.agile.configurable.condition.Condition;
import dev.octomc.agile.configurable.condition.ConditionSerializer;
import dev.octomc.agile.configurable.condition.impl.OrCondition;
import dev.octomc.agile.configurable.condition.impl.PermissionCondition;
import dev.octomc.agile.util.ItemStackGsonSerializer;
import dev.octomc.agile.util.ReturnCallback;
import lombok.Getter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class ConfigurableMenuManager {
    @Getter
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ItemStack.class, new ItemStackGsonSerializer())
            .registerTypeAdapter(Action.class, new ActionSerializer(this))
            .registerTypeAdapter(Condition.class, new ConditionSerializer(this))
            .registerTypeAdapter(MenuItemConfig.class, new MenuItemConfig.Serializer(this))
            .registerTypeAdapter(ConfigurableMenuManager.class, new ConfigurableMenuManagerSerializer(this)) // makeshift dependency injection
            .create();
    @Getter
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private Map<String, ReturnCallback<Action, JsonObject, JsonDeserializationContext>> actionRegistry = new HashMap<>();
    private Map<String, ReturnCallback<Condition, JsonObject, JsonDeserializationContext>> conditionRegistry = new HashMap<>();
    private Map<String, MenuConfig> menus = new HashMap<>();

    public ConfigurableMenuManager initDefaults() {
        actionRegistry.put("command", (jo, dC) -> dC.deserialize(jo, CommandAction.class));

        conditionRegistry.put("permission", (jo, dC) -> dC.deserialize(jo, PermissionCondition.class));
        conditionRegistry.put("or", (jo, dC) -> dC.deserialize(jo, OrCondition.class));
        conditionRegistry.put("and", (jo, dC) -> dC.deserialize(jo, OrCondition.class));
        conditionRegistry.put("not", (jo, dC) -> dC.deserialize(jo, OrCondition.class));
        conditionRegistry.put("xor", (jo, dC) -> dC.deserialize(jo, OrCondition.class));
        return this;
    }

    @SneakyThrows
    public ConfigurableMenuManager loadMenu(File file) {
        String name = file.getName().replace(".json", "");
        String json = new String(Files.readAllBytes(file.toPath()));
        MenuConfig menuConfig = gson.fromJson(json, MenuConfig.class);
        menus.put(name, menuConfig);
        return this;
    }

    public ConfigurableMenuManager loadDirectory(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            loadMenu(file);
        }
        return this;
    }

    public void open(String name, Player player) {
        if (!menus.containsKey(name)) {
            return;
        }
        MenuConfig menuConfig = menus.get(name);
        menuConfig.open(player, this);
    }
}

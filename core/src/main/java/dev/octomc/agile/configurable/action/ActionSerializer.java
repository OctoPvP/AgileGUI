package dev.octomc.agile.configurable.action;

import com.google.gson.*;
import lombok.AllArgsConstructor;
import dev.octomc.agile.configurable.ConfigurableMenuManager;

import java.lang.reflect.Type;

@AllArgsConstructor
public class ActionSerializer implements JsonSerializer<Action>, JsonDeserializer<Action> {
    private ConfigurableMenuManager manager;

    @Override
    public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString().toLowerCase();
        return manager.getActionRegistry().getOrDefault(type, (jo, jdc) -> null).call(jsonObject, context);
    }

    @Override
    public JsonElement serialize(Action src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(src).getAsJsonObject();
        jsonObject.addProperty("type", src.getClass().getSimpleName().replace("Action", "").toLowerCase());
        return jsonObject;
    }
}
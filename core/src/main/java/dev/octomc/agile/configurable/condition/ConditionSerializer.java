package dev.octomc.agile.configurable.condition;

import com.google.gson.*;
import lombok.AllArgsConstructor;
import dev.octomc.agile.configurable.ConfigurableMenuManager;

import java.lang.reflect.Type;

@AllArgsConstructor
public class ConditionSerializer implements JsonSerializer<Condition>, JsonDeserializer<Condition> {
    private ConfigurableMenuManager manager;

    @Override
    public Condition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString().toLowerCase();
        return manager.getConditionRegistry().get(type).call(jsonObject, context);
    }

    @Override
    public JsonElement serialize(Condition src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(src).getAsJsonObject();
        jsonObject.addProperty("type", src.getClass().getSimpleName().replace("Condition", "").toLowerCase());
        return jsonObject;
    }
}
package dev.octomc.agile.configurable;

import com.google.gson.*;
import lombok.AllArgsConstructor;

import java.lang.reflect.Type;

@AllArgsConstructor
public class ConfigurableMenuManagerSerializer implements JsonDeserializer<ConfigurableMenuManager>, JsonSerializer<ConfigurableMenuManagerSerializer> {
    private ConfigurableMenuManager manager;
    @Override
    public ConfigurableMenuManager deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return manager;
    }

    @Override
    public JsonElement serialize(ConfigurableMenuManagerSerializer configurableMenuManagerSerializer, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}

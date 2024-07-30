package dev.octomc.agile.util;

import com.google.gson.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackGsonSerializer /*implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack>*/ {
    /*@Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            String base64 = json.getAsString();
            return Base64Utils.fromBase64(base64);
        } else {
            JsonObject jsonObject = json.getAsJsonObject();
            Component name = jsonObject.has("name") ? MiniMessage.miniMessage().deserialize(jsonObject.get("name").getAsString()) : null;
            String material = jsonObject.get("material").getAsString();
            int amount = jsonObject.has("amount") ? jsonObject.get("amount").getAsInt() : 1;
            Map<String, Integer> enchants = null;
            if (jsonObject.has("enchants")) {
                enchants = new HashMap<>();
                JsonObject enchantsObject = jsonObject.get("enchants").getAsJsonObject();
                for (Map.Entry<String, JsonElement> stringJsonElementEntry : enchantsObject.entrySet()) {
                    String ench = stringJsonElementEntry.getKey();
                    int lvl = stringJsonElementEntry.getValue().getAsInt();
                    enchants.put(ench, lvl);
                }
            }
            List<Component> lore = new ArrayList<>();
            if (jsonObject.has("lore") && jsonObject.get("lore").isJsonArray()) {
                JsonArray loreArray = jsonObject.get("lore").getAsJsonArray();
                for (JsonElement jsonElement : loreArray) {
                    // lore.add(CC.translate(jsonElement.getAsString()));
                    lore.add(MiniMessage.miniMessage().deserialize(jsonElement.getAsString()));
                }
            }
            List<String> flags = new ArrayList<>();
            if (jsonObject.has("flags") && jsonObject.get("flags").isJsonArray()) {
                JsonArray flagsArray = jsonObject.get("flags").getAsJsonArray();
                for (JsonElement jsonElement : flagsArray) {
                    flags.add(jsonElement.getAsString());
                }
            }
            ItemStack item = new ItemStack(Material.valueOf(material), amount);
            ItemMeta meta = item.getItemMeta();
            if (name != null) {
                meta.setDisplayName(name);
            }
            for (String flag : flags) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
            meta.lore(lore);
            item.setItemMeta(meta);
            if (enchants != null) {
                for (Map.Entry<String, Integer> stringIntegerEntry : enchants.entrySet()) {
                    Enchantment enchantment = Enchantments.getByName(stringIntegerEntry.getKey());//Enchantment.getByName(stringIntegerEntry.getKey());
                    if (enchantment == null) continue;
                    item.addUnsafeEnchantment(enchantment, stringIntegerEntry.getValue());
                }
            }
            return item;
        }
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(Base64Utils.toBase64(src));
    }*/
}
package dev.octomc.agile.util;

import dev.triumphteam.gui.components.util.VersionHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Utils {
    public static ItemStack fromBase64(String base64) {
        byte[] data = Base64.getDecoder().decode(base64);
        if (VersionHelper.IS_ITEM_BYTES_API) {
            return ItemStack.deserializeBytes(data);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BukkitObjectInputStream dataInput = null;
        try {
            dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack itemStack = (ItemStack) dataInput.readObject();
            dataInput.close();
            return itemStack;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toBase64(ItemStack itemStack) {
        if (VersionHelper.IS_ITEM_BYTES_API) {
            return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes());
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(itemStack.serializeAsBytes());
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            String base64 = Base64.getEncoder().encodeToString(dataInput.readUTF().getBytes());
            dataInput.close();
            return base64;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

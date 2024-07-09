package dev.octomc.agile.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class ComponentUtils {
    public static Component cleanItalics(Component in) {
        return Component.empty().decoration(TextDecoration.ITALIC, false).append(in);
    }
}

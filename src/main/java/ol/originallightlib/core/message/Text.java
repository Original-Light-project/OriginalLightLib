package ol.originallightlib.core.message;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class Text {

    private Text() {
    }

    public static String color(String text) {
        if (text == null) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(List<String> lines) {
        List<String> colored = new ArrayList<>();

        for (String line : lines) {
            colored.add(color(line));
        }

        return colored;
    }

    public static String replace(String text, String... placeholders) {
        if (text == null) {
            return "";
        }

        if (placeholders == null || placeholders.length == 0) {
            return text;
        }

        String result = text;

        for (int i = 0; i < placeholders.length - 1; i += 2) {
            String key = placeholders[i];
            String value = placeholders[i + 1];

            result = result.replace(key, value);
        }

        return result;
    }

    public static String format(String text, String... placeholders) {
        return color(replace(text, placeholders));
    }

    public static List<String> format(List<String> lines, String... placeholders) {
        List<String> formatted = new ArrayList<>();

        for (String line : lines) {
            formatted.add(format(line, placeholders));
        }

        return formatted;
    }
}
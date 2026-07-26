package ol.originallightlib.core.message;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Text {

    private static final Pattern HEX_COLOR = Pattern.compile("(?i)&#([0-9a-f]{6})");

    private Text() {
    }

    public static String color(String text) {
        if (text == null) {
            return "";
        }

        Matcher matcher = HEX_COLOR.matcher(text);
        StringBuffer converted = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder legacy = new StringBuilder("§x");
            for (char character : hex.toCharArray()) {
                legacy.append('§').append(character);
            }
            matcher.appendReplacement(converted, Matcher.quoteReplacement(legacy.toString()));
        }
        matcher.appendTail(converted);
        return ChatColor.translateAlternateColorCodes('&', converted.toString());
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

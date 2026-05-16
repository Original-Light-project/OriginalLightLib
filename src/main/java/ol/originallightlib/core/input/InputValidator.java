package ol.originallightlib.core.input;

import java.util.Locale;
import java.util.regex.Pattern;

public final class InputValidator {

    private static final Pattern ID_PATTERN = Pattern.compile("^[a-z0-9_\\-]+$");

    private InputValidator() {
    }

    public static Integer parseInt(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static Long parseLong(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        try {
            return Long.parseLong(input.trim());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static Double parseDouble(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static boolean isInteger(String input) {
        return parseInt(input) != null;
    }

    public static boolean isLong(String input) {
        return parseLong(input) != null;
    }

    public static boolean isDouble(String input) {
        return parseDouble(input) != null;
    }

    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isInRange(long value, long min, long max) {
        return value >= min && value <= max;
    }

    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static boolean isPositive(int value) {
        return value > 0;
    }

    public static boolean isPositive(long value) {
        return value > 0;
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }

    public static boolean isNonNegative(int value) {
        return value >= 0;
    }

    public static boolean isNonNegative(long value) {
        return value >= 0;
    }

    public static boolean isNonNegative(double value) {
        return value >= 0;
    }

    public static boolean isValidId(String input) {
        if (input == null || input.isBlank()) {
            return false;
        }

        return ID_PATTERN.matcher(input.toLowerCase(Locale.ROOT)).matches();
    }

    public static String normalizeId(String input) {
        if (input == null) {
            return "";
        }

        return input.trim().toLowerCase(Locale.ROOT);
    }

    public static Boolean parseBoolean(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String normalized = input.trim().toLowerCase(Locale.ROOT);

        return switch (normalized) {
            case "true", "yes", "y", "on", "enable", "enabled", "是", "開", "啟用" -> true;
            case "false", "no", "n", "off", "disable", "disabled", "否", "關", "停用" -> false;
            default -> null;
        };
    }

    public static boolean isCancel(String input) {
        if (input == null) {
            return false;
        }

        String normalized = input.trim().toLowerCase(Locale.ROOT);

        return normalized.equals("cancel")
                || normalized.equals("取消")
                || normalized.equals("exit")
                || normalized.equals("quit");
    }
}
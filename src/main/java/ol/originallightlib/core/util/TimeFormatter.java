package ol.originallightlib.core.util;

public final class TimeFormatter {

    private TimeFormatter() {
    }

    public static String formatMillis(long millis) {
        if (millis <= 0) {
            return "0秒";
        }

        long totalSeconds = millis / 1000L;
        long remainingMillis = millis % 1000L;

        if (totalSeconds <= 0) {
            return remainingMillis + "毫秒";
        }

        if (totalSeconds < 10 && remainingMillis > 0) {
            double seconds = millis / 1000.0D;
            return String.format("%.1f秒", seconds);
        }

        return formatSeconds(totalSeconds);
    }

    public static String formatSeconds(long seconds) {
        if (seconds <= 0) {
            return "0秒";
        }

        long days = seconds / 86400L;
        seconds %= 86400L;

        long hours = seconds / 3600L;
        seconds %= 3600L;

        long minutes = seconds / 60L;
        long secs = seconds % 60L;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append("天");
        }

        if (hours > 0) {
            builder.append(hours).append("小時");
        }

        if (minutes > 0) {
            builder.append(minutes).append("分");
        }

        if (secs > 0 || builder.isEmpty()) {
            builder.append(secs).append("秒");
        }

        return builder.toString();
    }

    public static String formatSecondsShort(long seconds) {
        if (seconds <= 0) {
            return "0s";
        }

        long days = seconds / 86400L;
        seconds %= 86400L;

        long hours = seconds / 3600L;
        seconds %= 3600L;

        long minutes = seconds / 60L;
        long secs = seconds % 60L;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append("d ");
        }

        if (hours > 0) {
            builder.append(hours).append("h ");
        }

        if (minutes > 0) {
            builder.append(minutes).append("m ");
        }

        if (secs > 0 || builder.isEmpty()) {
            builder.append(secs).append("s");
        }

        return builder.toString().trim();
    }

    public static String formatTicks(long ticks) {
        if (ticks <= 0) {
            return "0秒";
        }

        long millis = ticks * 50L;
        return formatMillis(millis);
    }

    public static String formatClock(long seconds) {
        if (seconds <= 0) {
            return "00:00";
        }

        long days = seconds / 86400L;
        seconds %= 86400L;

        long hours = seconds / 3600L;
        long minutes = (seconds % 3600L) / 60L;
        long secs = seconds % 60L;

        String clock;

        if (hours > 0 || days > 0) {
            clock = String.format("%02d:%02d:%02d", hours, minutes, secs);
        } else {
            clock = String.format("%02d:%02d", minutes, secs);
        }

        if (days > 0) {
            return clock + " (+" + days + " Day" + (days > 1 ? "s" : "") + ")";
        }

        return clock;
    }
}
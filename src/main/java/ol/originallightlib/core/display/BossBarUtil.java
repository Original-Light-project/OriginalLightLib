package ol.originallightlib.core.display;

import ol.originallightlib.core.message.Text;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Collection;

public final class BossBarUtil {

    private BossBarUtil() {
    }

    public static BossBarHandle create(String title) {
        return create(title, BarColor.GREEN, BarStyle.SOLID, 1.0D);
    }

    public static BossBarHandle create(String title, double progress) {
        return create(title, BarColor.GREEN, BarStyle.SOLID, progress);
    }

    public static BossBarHandle create(String title, BarColor color, BarStyle style) {
        return create(title, color, style, 1.0D);
    }

    public static BossBarHandle create(String title, BarColor color, BarStyle style, double progress) {
        BossBar bossBar = Bukkit.createBossBar(
                Text.color(title),
                color == null ? BarColor.GREEN : color,
                style == null ? BarStyle.SOLID : style
        );

        bossBar.setProgress(clamp(progress));
        bossBar.setVisible(true);

        return new BossBarHandle(bossBar);
    }

    public static BossBarHandle create(Player player, String title) {
        return create(player, title, BarColor.GREEN, BarStyle.SOLID, 1.0D);
    }

    public static BossBarHandle create(Player player, String title, double progress) {
        return create(player, title, BarColor.GREEN, BarStyle.SOLID, progress);
    }

    public static BossBarHandle create(
            Player player,
            String title,
            BarColor color,
            BarStyle style,
            double progress
    ) {
        BossBarHandle handle = create(title, color, style, progress);
        handle.addPlayer(player);

        return handle;
    }

    public static BossBarHandle create(
            Collection<? extends Player> players,
            String title,
            BarColor color,
            BarStyle style,
            double progress
    ) {
        BossBarHandle handle = create(title, color, style, progress);
        handle.addPlayers(players);

        return handle;
    }

    private static double clamp(double value) {
        if (value < 0.0D) {
            return 0.0D;
        }

        if (value > 1.0D) {
            return 1.0D;
        }

        return value;
    }
}
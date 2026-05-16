package ol.originallightlib.core.display;

import ol.originallightlib.core.message.Text;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BossBarHandle {

    private final BossBar bossBar;

    BossBarHandle(BossBar bossBar) {
        this.bossBar = bossBar;
    }

    public BossBarHandle addPlayer(Player player) {
        if (player == null || !player.isOnline()) {
            return this;
        }

        bossBar.addPlayer(player);
        return this;
    }

    public BossBarHandle addPlayers(Collection<? extends Player> players) {
        if (players == null || players.isEmpty()) {
            return this;
        }

        for (Player player : players) {
            addPlayer(player);
        }

        return this;
    }

    public BossBarHandle removePlayer(Player player) {
        if (player == null) {
            return this;
        }

        bossBar.removePlayer(player);
        return this;
    }

    public BossBarHandle setTitle(String title) {
        bossBar.setTitle(Text.color(title));
        return this;
    }

    public BossBarHandle setTitle(String title, String... placeholders) {
        bossBar.setTitle(Text.format(title, placeholders));
        return this;
    }

    public BossBarHandle setProgress(double progress) {
        bossBar.setProgress(clamp(progress));
        return this;
    }

    public BossBarHandle setVisible(boolean visible) {
        bossBar.setVisible(visible);
        return this;
    }

    public BossBarHandle removeAll() {
        bossBar.removeAll();
        return this;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public double getProgress() {
        return bossBar.getProgress();
    }

    public void remove() {
        bossBar.removeAll();
        bossBar.setVisible(false);
    }

    private double clamp(double value) {
        if (value < 0.0D) {
            return 0.0D;
        }

        if (value > 1.0D) {
            return 1.0D;
        }

        return value;
    }
}
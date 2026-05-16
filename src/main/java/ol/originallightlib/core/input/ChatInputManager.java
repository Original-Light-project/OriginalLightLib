package ol.originallightlib.core.input;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChatInputManager {

    private final JavaPlugin plugin;
    private final Map<UUID, ChatInputSession> sessions = new HashMap<>();

    public ChatInputManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(new ChatInputListener(this, plugin), plugin);
    }

    public void awaitInput(
            Player player,
            String prompt,
            BiConsumer<Player, String> inputAction
    ) {
        awaitInput(player, prompt, inputAction, null, 60);
    }

    public void awaitInput(
            Player player,
            String prompt,
            BiConsumer<Player, String> inputAction,
            Consumer<Player> cancelAction,
            int timeoutSeconds
    ) {
        long expireAtMillis = System.currentTimeMillis() + timeoutSeconds * 1000L;

        ChatInputSession session = new ChatInputSession(
                prompt,
                inputAction,
                cancelAction,
                expireAtMillis
        );

        sessions.put(player.getUniqueId(), session);

        if (prompt != null && !prompt.isBlank()) {
            player.sendMessage(prompt);
        }

        player.sendMessage("§7輸入 §ccancel §7可取消。");
    }

    public boolean hasSession(Player player) {
        return sessions.containsKey(player.getUniqueId());
    }

    public ChatInputSession getSession(Player player) {
        return sessions.get(player.getUniqueId());
    }

    public void removeSession(Player player) {
        sessions.remove(player.getUniqueId());
    }

    public void cancel(Player player) {
        ChatInputSession session = sessions.remove(player.getUniqueId());

        if (session == null) {
            return;
        }

        if (session.getCancelAction() != null) {
            session.getCancelAction().accept(player);
        } else {
            player.sendMessage("§7已取消輸入。");
        }
    }
}
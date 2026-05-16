package ol.originallightlib.core.input;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatInputListener implements Listener {

    private final ChatInputManager chatInputManager;
    private final JavaPlugin plugin;

    public ChatInputListener(ChatInputManager chatInputManager, JavaPlugin plugin) {
        this.chatInputManager = chatInputManager;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!chatInputManager.hasSession(player)) {
            return;
        }

        event.setCancelled(true);

        String message = event.getMessage();
        ChatInputSession session = chatInputManager.getSession(player);

        if (session == null) {
            return;
        }

        if (session.isExpired()) {
            chatInputManager.removeSession(player);

            Bukkit.getScheduler().runTask(plugin, () -> {
                player.sendMessage("§c輸入已逾時，請重新操作。");
            });
            return;
        }

        if (message.equalsIgnoreCase("cancel") || message.equalsIgnoreCase("取消")) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                chatInputManager.cancel(player);
            });
            return;
        }

        chatInputManager.removeSession(player);

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (session.getInputAction() != null) {
                session.getInputAction().accept(player, message);
            }
        });
    }
}
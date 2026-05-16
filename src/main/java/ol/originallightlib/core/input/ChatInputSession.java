package ol.originallightlib.core.input;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChatInputSession {

    private final String prompt;
    private final BiConsumer<Player, String> inputAction;
    private final Consumer<Player> cancelAction;
    private final long expireAtMillis;

    public ChatInputSession(
            String prompt,
            BiConsumer<Player, String> inputAction,
            Consumer<Player> cancelAction,
            long expireAtMillis
    ) {
        this.prompt = prompt;
        this.inputAction = inputAction;
        this.cancelAction = cancelAction;
        this.expireAtMillis = expireAtMillis;
    }

    public String getPrompt() {
        return prompt;
    }

    public BiConsumer<Player, String> getInputAction() {
        return inputAction;
    }

    public Consumer<Player> getCancelAction() {
        return cancelAction;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireAtMillis;
    }
}
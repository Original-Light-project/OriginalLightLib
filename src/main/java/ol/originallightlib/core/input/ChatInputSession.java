package ol.originallightlib.core.input;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChatInputSession {

    private final String prompt;
    private final BiConsumer<Player, String> inputAction;
    private final Consumer<Player> cancelAction;
    private final long expireAtMillis;
    private final String cancelToken;
    private final String timeoutMessage;

    public ChatInputSession(
            String prompt,
            BiConsumer<Player, String> inputAction,
            Consumer<Player> cancelAction,
            long expireAtMillis
    ) {
        this(prompt, inputAction, cancelAction, expireAtMillis, "cancel", "§c輸入已逾時，請重新操作。");
    }

    public ChatInputSession(
            String prompt,
            BiConsumer<Player, String> inputAction,
            Consumer<Player> cancelAction,
            long expireAtMillis,
            String cancelToken,
            String timeoutMessage
    ) {
        this.prompt = prompt;
        this.inputAction = inputAction;
        this.cancelAction = cancelAction;
        this.expireAtMillis = expireAtMillis;
        this.cancelToken = cancelToken == null || cancelToken.isBlank() ? "cancel" : cancelToken;
        this.timeoutMessage = timeoutMessage == null ? "" : timeoutMessage;
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

    public String getCancelToken() {
        return cancelToken;
    }

    public String getTimeoutMessage() {
        return timeoutMessage;
    }
}

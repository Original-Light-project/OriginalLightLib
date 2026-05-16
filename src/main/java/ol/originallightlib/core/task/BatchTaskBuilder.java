package ol.originallightlib.core.task;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BatchTaskBuilder<T> {

    private final List<T> items;

    private int perTick = 100;
    private long interval = 1L;

    private Consumer<T> eachAction;
    private Runnable completeAction;
    private Consumer<BatchTask<T>> tickAction;

    BatchTaskBuilder(List<T> items) {
        this.items = items == null ? List.of() : new ArrayList<>(items);
    }

    public BatchTaskBuilder<T> perTick(int perTick) {
        if (perTick <= 0) {
            throw new IllegalArgumentException("perTick must be greater than 0.");
        }

        this.perTick = perTick;
        return this;
    }

    public BatchTaskBuilder<T> interval(long interval) {
        if (interval <= 0) {
            throw new IllegalArgumentException("interval must be greater than 0.");
        }

        this.interval = interval;
        return this;
    }

    public BatchTaskBuilder<T> onEach(Consumer<T> eachAction) {
        this.eachAction = eachAction;
        return this;
    }

    public BatchTaskBuilder<T> onComplete(Runnable completeAction) {
        this.completeAction = completeAction;
        return this;
    }

    public BatchTaskBuilder<T> onTick(Consumer<BatchTask<T>> tickAction) {
        this.tickAction = tickAction;
        return this;
    }

    public BatchTask<T> build(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null.");
        }

        return new BatchTask<>(
                plugin,
                items,
                perTick,
                interval,
                eachAction,
                completeAction,
                tickAction
        );
    }

    public BatchTask<T> start(JavaPlugin plugin) {
        BatchTask<T> task = build(plugin);
        task.start();
        return task;
    }
}
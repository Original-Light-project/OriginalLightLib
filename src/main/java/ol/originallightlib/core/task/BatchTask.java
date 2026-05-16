package ol.originallightlib.core.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.function.Consumer;

public class BatchTask<T> {

    private final JavaPlugin plugin;
    private final List<T> items;
    private final int perTick;
    private final long interval;
    private final Consumer<T> eachAction;
    private final Runnable completeAction;
    private final Consumer<BatchTask<T>> tickAction;

    private int index = 0;
    private BukkitTask task;
    private boolean cancelled = false;

    BatchTask(
            JavaPlugin plugin,
            List<T> items,
            int perTick,
            long interval,
            Consumer<T> eachAction,
            Runnable completeAction,
            Consumer<BatchTask<T>> tickAction
    ) {
        this.plugin = plugin;
        this.items = items;
        this.perTick = perTick;
        this.interval = interval;
        this.eachAction = eachAction;
        this.completeAction = completeAction;
        this.tickAction = tickAction;
    }

    public void start() {
        if (items == null || items.isEmpty()) {
            if (completeAction != null) {
                completeAction.run();
            }
            return;
        }

        if (eachAction == null) {
            throw new IllegalStateException("BatchTask requires an eachAction.");
        }

        this.task = Bukkit.getScheduler().runTaskTimer(
                plugin,
                this::runTick,
                0L,
                interval
        );
    }

    private void runTick() {
        if (cancelled) {
            stopTask();
            return;
        }

        int processed = 0;

        while (processed < perTick && index < items.size()) {
            T item = items.get(index);

            eachAction.accept(item);

            index++;
            processed++;
        }

        if (tickAction != null) {
            tickAction.accept(this);
        }

        if (index >= items.size()) {
            stopTask();

            if (completeAction != null) {
                completeAction.run();
            }
        }
    }

    public void cancel() {
        this.cancelled = true;
        stopTask();
    }

    private void stopTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public int getProcessedCount() {
        return index;
    }

    public int getTotalCount() {
        return items == null ? 0 : items.size();
    }

    public int getRemainingCount() {
        return Math.max(0, getTotalCount() - getProcessedCount());
    }

    public double getProgress() {
        if (getTotalCount() <= 0) {
            return 1.0D;
        }

        return (double) getProcessedCount() / (double) getTotalCount();
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isComplete() {
        return getProcessedCount() >= getTotalCount();
    }

    public static <T> BatchTaskBuilder<T> of(List<T> items) {
        return new BatchTaskBuilder<>(items);
    }
}
# OriginalLightLib Javadoc API Overview

> 本文件是 OriginalLightLib 初版核心 API 的 Javadoc 草稿。  
> 用途是幫助後續整理成 `package-info.java`、類別 Javadoc，或作為外部插件依賴 OLL 時的 API 參考。

---

# Package Overview

## `ol.originallightlib`

OriginalLightLib 的插件主入口 package。

主要包含：

- `OriginalLightLib`

此 package 負責初始化 OLL 的核心模組，包括 GUI、指令、訊息、物品、選區、冷卻、聊天輸入與 debug 設定。

### 建議 package-info.java

```java
/**
 * OriginalLightLib plugin root package.
 * <p>
 * This package contains the plugin bootstrap class and exposes shared managers
 * used by other modules and dependent plugins.
 * </p>
 */
package ol.originallightlib;
```

---

# Main Plugin Class

## `OriginalLightLib`

```java
/**
 * Main entry point of the OriginalLightLib plugin.
 * <p>
 * This class initializes and exposes the core managers used by the library,
 * including GUI, command, message, item registry, chat input, selection,
 * cooldown, and debug settings.
 * </p>
 *
 * <p>Common access pattern:</p>
 * <pre>{@code
 * OriginalLightLib plugin = OriginalLightLib.getInstance();
 * GuiManager guiManager = plugin.getGuiManager();
 * }</pre>
 */
public final class OriginalLightLib extends JavaPlugin
```

### Exposed Managers

```java
/**
 * Gets the singleton plugin instance.
 *
 * @return the current OriginalLightLib instance
 */
public static OriginalLightLib getInstance()
```

```java
/**
 * Gets the GUI manager used to track and dispatch custom inventory GUI events.
 *
 * @return the GUI manager
 */
public GuiManager getGuiManager()
```

```java
/**
 * Gets the command manager responsible for subcommand dispatching.
 *
 * @return the command manager
 */
public CommandManager getCommandManager()
```

```java
/**
 * Gets the message manager used for messages.yml based message formatting.
 *
 * @return the message manager
 */
public MessageManager getMessageManager()
```

```java
/**
 * Gets the item registry owned by OriginalLightLib.
 * <p>
 * Dependent plugins may create their own ItemRegistry instance if they want
 * plugin-local item storage.
 * </p>
 *
 * @return the default OLL item registry
 */
public ItemRegistry getItemRegistry()
```

```java
/**
 * Gets the chat input manager used to capture the next chat message from players.
 *
 * @return the chat input manager
 */
public ChatInputManager getChatInputManager()
```

```java
/**
 * Gets the selection manager used to track temporary player selections.
 *
 * @return the selection manager
 */
public SelectionManager getSelectionManager()
```

```java
/**
 * Gets the cooldown manager used to track player-specific cooldowns.
 *
 * @return the cooldown manager
 */
public CooldownManager getCooldownManager()
```

```java
/**
 * Gets the debug settings loaded from config.yml.
 *
 * @return the debug settings
 */
public DebugSettings getDebugSettings()
```

---

# `core.command`

Command framework for registering and dispatching subcommands under a root Bukkit command.

### 建議 package-info.java

```java
/**
 * Provides a lightweight subcommand framework.
 * <p>
 * The command framework maps the first argument of a root command to a
 * registered {@link ol.originallightlib.core.command.SubCommand}, handles
 * permission checks, and delegates tab completion.
 * </p>
 */
package ol.originallightlib.core.command;
```

---

## `CommandContext`

```java
/**
 * Immutable command execution context passed to subcommands.
 * <p>
 * This class wraps Bukkit command execution arguments into a single object,
 * making subcommand implementations cleaner and easier to maintain.
 * </p>
 */
public class CommandContext
```

### Key Methods

```java
/**
 * Gets the sender who executed the command.
 *
 * @return command sender
 */
public CommandSender sender()
```

```java
/**
 * Gets the raw command arguments passed to the subcommand.
 *
 * @return subcommand arguments
 */
public String[] args()
```

```java
/**
 * Gets the argument at the given index.
 *
 * @param index argument index
 * @return argument value, or null if out of bounds
 */
public String arg(int index)
```

```java
/**
 * Gets the number of arguments passed to this subcommand.
 *
 * @return argument length
 */
public int length()
```

---

## `SubCommand`

```java
/**
 * Represents a subcommand handled by {@link CommandManager}.
 * <p>
 * Implementations define their command name, description, usage, permission,
 * execution logic, and optional tab completion.
 * </p>
 */
public interface SubCommand
```

### Required Methods

```java
/**
 * Gets the subcommand name.
 *
 * @return subcommand name
 */
String name();
```

```java
/**
 * Gets a short description of the subcommand.
 *
 * @return subcommand description
 */
String description();
```

```java
/**
 * Gets the usage text displayed in help messages.
 *
 * @return usage string
 */
String usage();
```

```java
/**
 * Gets the permission required to use this subcommand.
 *
 * @return permission node, or null/blank if no permission is required
 */
String permission();
```

```java
/**
 * Executes the subcommand.
 *
 * @param context command context
 */
void execute(CommandContext context);
```

---

## `CommandManager`

```java
/**
 * Dispatches root command calls to registered subcommands.
 * <p>
 * This class implements both {@link org.bukkit.command.CommandExecutor} and
 * {@link org.bukkit.command.TabCompleter}. It uses the first command argument
 * as a subcommand key.
 * </p>
 */
public class CommandManager implements CommandExecutor, TabCompleter
```

### Key Methods

```java
/**
 * Registers a subcommand.
 *
 * @param subCommand subcommand to register
 */
public void register(SubCommand subCommand)
```

```java
/**
 * Gets all registered subcommands.
 *
 * @return registered subcommands
 */
public Collection<SubCommand> getSubCommands()
```

---

# `core.config`

YAML configuration helpers.

### 建議 package-info.java

```java
/**
 * Provides simple YAML file wrappers for Bukkit configuration files.
 */
package ol.originallightlib.core.config;
```

---

## `YamlFile`

```java
/**
 * Utility wrapper around a plugin-owned YAML file.
 * <p>
 * This class handles file creation, loading, saving, and reloading.
 * </p>
 */
public class YamlFile
```

### Key Methods

```java
/**
 * Loads the YAML file from disk, creating it if it does not exist.
 */
public void load()
```

```java
/**
 * Saves the current configuration to disk.
 */
public void save()
```

```java
/**
 * Reloads the configuration from disk.
 */
public void reload()
```

```java
/**
 * Gets the backing YAML configuration.
 *
 * @return YAML configuration
 */
public YamlConfiguration getConfig()
```

---

# `core.debug`

Debug mode and development command controls.

### 建議 package-info.java

```java
/**
 * Provides debug-mode configuration and verbose logging helpers.
 */
package ol.originallightlib.core.debug;
```

---

## `DebugSettings`

```java
/**
 * Represents debug-related settings loaded from config.yml.
 * <p>
 * Debug settings control whether development commands are registered and
 * whether verbose debug logs are printed.
 * </p>
 */
public class DebugSettings
```

### Key Methods

```java
/**
 * Reloads debug settings from config.yml.
 */
public void reload()
```

```java
/**
 * Checks whether debug mode is enabled.
 *
 * @return true if debug mode is enabled
 */
public boolean isEnabled()
```

```java
/**
 * Checks whether test commands should be registered.
 *
 * @return true if debug mode and test commands are both enabled
 */
public boolean isTestCommandsEnabled()
```

```java
/**
 * Logs a debug message if verbose logging is enabled.
 *
 * @param message message to log
 */
public void log(String message)
```

---

# `core.cooldown`

Player-specific cooldown management.

### 建議 package-info.java

```java
/**
 * Provides player-specific cooldown tracking utilities.
 */
package ol.originallightlib.core.cooldown;
```

---

## `CooldownManager`

```java
/**
 * Tracks named cooldowns for players.
 * <p>
 * Cooldowns are stored in memory and keyed by player UUID and a custom string key.
 * </p>
 */
public class CooldownManager
```

### Usage

```java
CooldownManager cooldowns = OriginalLightLib.getInstance().getCooldownManager();

if (cooldowns.hasCooldown(player, "interaction")) {
    long remaining = cooldowns.getRemainingSecondsRoundedUp(player, "interaction");
    return;
}

cooldowns.setCooldown(player, "interaction", 5);
```

### Key Methods

```java
/**
 * Sets a cooldown for the given player and key.
 *
 * @param player player
 * @param key cooldown key
 * @param seconds duration in seconds
 */
public void setCooldown(Player player, String key, long seconds)
```

```java
/**
 * Checks whether the player currently has an active cooldown for the key.
 * Expired cooldowns are automatically removed.
 *
 * @param player player
 * @param key cooldown key
 * @return true if cooldown is active
 */
public boolean hasCooldown(Player player, String key)
```

```java
/**
 * Gets the remaining cooldown time in milliseconds.
 *
 * @param player player
 * @param key cooldown key
 * @return remaining milliseconds, or 0 if no cooldown exists
 */
public long getRemainingMillis(Player player, String key)
```

```java
/**
 * Removes a specific cooldown from a player.
 *
 * @param player player
 * @param key cooldown key
 */
public void removeCooldown(Player player, String key)
```

---

# `core.display`

Player feedback utilities including ActionBar, BossBar, sounds, and titles.

### 建議 package-info.java

```java
/**
 * Provides player-facing display and feedback utilities such as ActionBar,
 * BossBar, sounds, and titles.
 */
package ol.originallightlib.core.display;
```

---

## `ActionBar`

```java
/**
 * Utility class for sending action bar messages to players.
 * <p>
 * This implementation uses the Spigot/Bungee action bar API for compatibility
 * with Spigot-based servers.
 * </p>
 */
public final class ActionBar
```

### Key Methods

```java
/**
 * Sends an action bar message to a player.
 *
 * @param player target player
 * @param message message with legacy color codes
 */
public static void send(Player player, String message)
```

```java
/**
 * Sends a formatted action bar message to a player.
 *
 * @param player target player
 * @param message message with placeholders
 * @param placeholders placeholder pairs
 */
public static void send(Player player, String message, String... placeholders)
```

---

## `BossBarUtil`

```java
/**
 * Factory utility for creating BossBar handles.
 */
public final class BossBarUtil
```

### Usage

```java
BossBarHandle bar = BossBarUtil.create(player, "&aProcessing...", 0.0D);
bar.setProgress(0.5D);
bar.setTitle("&aProcessing: &e50%");
bar.remove();
```

---

## `BossBarHandle`

```java
/**
 * Mutable handle around a Bukkit BossBar.
 * <p>
 * Provides fluent methods for updating title, progress, visibility, and players.
 * </p>
 */
public class BossBarHandle
```

### Key Methods

```java
/**
 * Adds a player to this BossBar.
 *
 * @param player player to add
 * @return this handle
 */
public BossBarHandle addPlayer(Player player)
```

```java
/**
 * Sets BossBar progress.
 * Values are clamped to the range 0.0 to 1.0.
 *
 * @param progress progress value
 * @return this handle
 */
public BossBarHandle setProgress(double progress)
```

```java
/**
 * Removes the BossBar from all players and hides it.
 */
public void remove()
```

---

## `SoundUtil`

```java
/**
 * Utility class for common sound feedback patterns.
 */
public final class SoundUtil
```

### Common Methods

```java
public static void click(Player player)
public static void success(Player player)
public static void error(Player player)
public static void cancel(Player player)
public static void select(Player player)
public static void complete(Player player)
```

---

## `TitleUtil`

```java
/**
 * Utility class for sending title and subtitle messages.
 */
public final class TitleUtil
```

### Key Methods

```java
/**
 * Sends a title and subtitle to a player.
 *
 * @param player target player
 * @param title title text
 * @param subtitle subtitle text
 * @param fadeIn fade-in ticks
 * @param stay stay ticks
 * @param fadeOut fade-out ticks
 */
public static void send(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
```

---

# `core.gui`

Inventory GUI framework.

### 建議 package-info.java

```java
/**
 * Provides a reusable inventory GUI framework.
 * <p>
 * The GUI framework handles inventory creation, click dispatching, close handling,
 * GUI item protection, and player GUI tracking.
 * </p>
 */
package ol.originallightlib.core.gui;
```

---

## `Gui`

```java
/**
 * Base class for all custom inventory GUIs.
 * <p>
 * Subclasses implement {@link #draw(Player)} to define buttons and layout.
 * </p>
 */
public abstract class Gui
```

### Usage

```java
public class ExampleGui extends Gui {
    public ExampleGui() {
        super("Example", 27);
    }

    @Override
    protected void draw(Player player) {
        setButton(13, new GuiButton(icon, event -> {
            player.sendMessage("Clicked!");
        }));
    }
}
```

### Key Methods

```java
/**
 * Opens this GUI for the given player.
 *
 * @param player player to open the GUI for
 */
public void open(Player player)
```

```java
/**
 * Draws the GUI contents.
 *
 * @param player player viewing the GUI
 */
protected abstract void draw(Player player)
```

```java
/**
 * Sets a clickable button at a slot.
 *
 * @param slot inventory slot
 * @param button button to set
 */
protected void setButton(int slot, GuiButton button)
```

```java
/**
 * Called when the player closes the GUI.
 *
 * @param player player who closed the GUI
 */
public void onClose(Player player)
```

---

## `GuiButton`

```java
/**
 * Represents a clickable GUI button.
 * <p>
 * A button consists of an icon and a click action.
 * </p>
 */
public class GuiButton
```

---

## `GuiManager`

```java
/**
 * Tracks currently opened GUIs and registers GUI listeners.
 */
public class GuiManager
```

---

## `GuiHolder`

```java
/**
 * InventoryHolder implementation used to bind a Bukkit Inventory to a Gui.
 * <p>
 * This allows listeners to identify GUI inventories even when GuiManager state
 * is unavailable due to inventory close timing.
 * </p>
 */
public class GuiHolder implements InventoryHolder
```

---

## `GuiItemMarker`

```java
/**
 * Marks GUI icon items using PersistentDataContainer.
 * <p>
 * Marked GUI items can be detected and removed if they accidentally enter a
 * player's inventory due to client/server edge cases.
 * </p>
 */
public final class GuiItemMarker
```

---

# `core.gui.common`

Common reusable GUI components.

## `ConfirmGui`

```java
/**
 * Reusable confirmation GUI.
 * <p>
 * Provides confirm, cancel, optional back action, customizable icons, title,
 * description, and close behavior.
 * </p>
 */
public class ConfirmGui extends Gui
```

### Usage

```java
ConfirmGui.builder()
    .title("§8Confirm Delete")
    .description("&7Are you sure?", "", "&cThis cannot be undone.")
    .onConfirm(player -> delete())
    .onCancel(player -> player.sendMessage("Cancelled."))
    .open(player);
```

---

# `core.gui.page`

Paginated GUI framework.

## `PaginatedGui<T>`

```java
/**
 * Base class for paginated inventory GUIs.
 * <p>
 * This class handles page state, navigation buttons, page information, and
 * empty-list display. Subclasses define how each item is rendered.
 * </p>
 *
 * @param <T> item type displayed in the GUI
 */
public abstract class PaginatedGui<T> extends Gui
```

### Required Override

```java
/**
 * Creates a button for one item on the current page.
 *
 * @param player viewer
 * @param item item to render
 * @param index absolute item index
 * @return GUI button for the item
 */
protected abstract GuiButton createItemButton(Player player, T item, int index)
```

---

# `core.gui.selector`

Selection GUI components.

## `ItemSelectGui`

```java
/**
 * Paginated GUI for selecting an item ID from an ItemRegistry.
 * <p>
 * When an item is selected, the configured callback receives the player and
 * selected item ID.
 * </p>
 */
public class ItemSelectGui extends PaginatedGui<String>
```

### Usage

```java
new ItemSelectGui(itemRegistry, (player, itemId) -> {
    player.sendMessage("Selected: " + itemId);
    player.closeInventory();
}).open(player);
```

---

# `core.input`

Chat input and input validation utilities.

### 建議 package-info.java

```java
/**
 * Provides chat-based player input sessions and validation utilities.
 */
package ol.originallightlib.core.input;
```

---

## `ChatInputManager`

```java
/**
 * Manages pending chat input sessions for players.
 * <p>
 * A chat input session captures a player's next chat message, cancels the
 * normal chat broadcast, and passes the message to a callback on the main thread.
 * </p>
 */
public class ChatInputManager
```

### Usage

```java
OriginalLightLib.getInstance().getChatInputManager().awaitInput(
    player,
    "§ePlease enter a number:",
    (target, input) -> {
        target.sendMessage("Input: " + input);
    }
);
```

---

## `ChatInputSession`

```java
/**
 * Represents one pending chat input session.
 */
public class ChatInputSession
```

---

## `InputValidator`

```java
/**
 * Utility class for parsing and validating common player input values.
 */
public final class InputValidator
```

### Common Methods

```java
public static Integer parseInt(String input)
public static Long parseLong(String input)
public static Double parseDouble(String input)
public static boolean isValidId(String input)
public static String normalizeId(String input)
public static Boolean parseBoolean(String input)
public static boolean isCancel(String input)
```

---

# `core.item`

Item construction, matching, and registry utilities.

### 建議 package-info.java

```java
/**
 * Provides ItemStack builders, comparison helpers, and YAML-backed item registries.
 */
package ol.originallightlib.core.item;
```

---

## `ItemBuilder`

```java
/**
 * Fluent builder for creating and modifying ItemStack instances.
 */
public class ItemBuilder
```

### Usage

```java
ItemStack item = ItemBuilder.of(Material.CHEST)
    .name("&aEdit Material")
    .lore("&7Click to edit", "", "&eClick")
    .glow()
    .build();
```

---

## `ItemMatcher`

```java
/**
 * Utility class for comparing and consuming similar ItemStack instances.
 * <p>
 * Similarity ignores item amount but respects material and item meta.
 * </p>
 */
public final class ItemMatcher
```

### Key Methods

```java
public static boolean isSimilar(ItemStack source, ItemStack target)
public static int countItem(Player player, ItemStack targetItem)
public static boolean hasItem(Player player, ItemStack targetItem, int amount)
public static boolean removeItem(Player player, ItemStack targetItem, int amount)
```

---

## `ItemRegistry`

```java
/**
 * YAML-backed registry for named ItemStack templates.
 * <p>
 * Each registered item is saved with amount 1 and can later be retrieved by ID.
 * Dependent plugins may create their own registry instance to store items in
 * their own plugin data folder.
 * </p>
 */
public class ItemRegistry
```

### Usage

```java
ItemRegistry registry = new ItemRegistry(plugin, "items.yml");
registry.saveItem("repair_stone", item);
ItemStack template = registry.getItem("repair_stone");
```

---

# `core.message`

Message and text formatting utilities.

### 建議 package-info.java

```java
/**
 * Provides message formatting and messages.yml based message management.
 */
package ol.originallightlib.core.message;
```

---

## `Text`

```java
/**
 * Utility class for legacy color translation and placeholder replacement.
 */
public final class Text
```

### Common Methods

```java
public static String color(String text)
public static List<String> color(List<String> lines)
public static String replace(String text, String... placeholders)
public static String format(String text, String... placeholders)
public static List<String> format(List<String> lines, String... placeholders)
```

---

## `MessageManager`

```java
/**
 * Manages messages loaded from messages.yml.
 * <p>
 * Supports a global prefix and placeholder replacement.
 * </p>
 */
public class MessageManager
```

### Usage

```java
messageManager.send(sender, "event.created", "%event%", eventId);
```

---

# `core.region`

Selection and cuboid region utilities.

### 建議 package-info.java

```java
/**
 * Provides player selection tools, cuboid region data structures, and YAML
 * serialization helpers for block positions and regions.
 */
package ol.originallightlib.core.region;
```

---

## `BlockPosition`

```java
/**
 * Immutable block coordinate representation.
 * <p>
 * Stores only world name and integer block coordinates.
 * </p>
 */
public class BlockPosition
```

---

## `CuboidRegion`

```java
/**
 * Represents a cuboid region defined by two block positions in the same world.
 */
public class CuboidRegion
```

### Key Methods

```java
public boolean contains(Location location)
public long getVolume()
public int getMinX()
public int getMaxX()
public int getMinY()
public int getMaxY()
public int getMinZ()
public int getMaxZ()
```

---

## `PlayerSelection`

```java
/**
 * Represents a temporary two-point selection owned by a player.
 */
public class PlayerSelection
```

---

## `SelectionManager`

```java
/**
 * Tracks temporary selections for players.
 */
public class SelectionManager
```

### Key Methods

```java
public PlayerSelection getSelection(Player player)
public void setPos1(Player player, Location location)
public void setPos2(Player player, Location location)
public void setSelection(Player player, PlayerSelection selection)
public void clearSelection(Player player)
public CuboidRegion getRegion(Player player)
```

---

## `SelectionTool`

```java
/**
 * Creates and identifies the OLL selection tool using PersistentDataContainer.
 */
public final class SelectionTool
```

### Key Methods

```java
public static ItemStack createTool()
public static ItemStack mark(ItemStack itemStack)
public static boolean isSelectionTool(ItemStack itemStack)
```

---

## `SelectionListener`

```java
/**
 * Handles left and right click selection using the marked selection tool.
 * <p>
 * Left-click sets pos1 and right-click sets pos2.
 * </p>
 */
public class SelectionListener implements Listener
```

---

## `RegionSerializer`

```java
/**
 * Utility class for saving and loading block positions, player selections,
 * and cuboid regions to and from YAML configurations.
 */
public final class RegionSerializer
```

### Common Methods

```java
public static void saveBlockPosition(FileConfiguration config, String path, BlockPosition position)
public static BlockPosition loadBlockPosition(FileConfiguration config, String path)
public static void saveCuboidRegion(FileConfiguration config, String path, CuboidRegion region)
public static CuboidRegion loadCuboidRegion(FileConfiguration config, String path)
public static void savePlayerSelection(FileConfiguration config, String path, PlayerSelection selection)
public static PlayerSelection loadPlayerSelection(FileConfiguration config, String path)
```

---

# `core.task`

Batch processing utilities.

### 建議 package-info.java

```java
/**
 * Provides tick-based batch task utilities for spreading large workloads across
 * multiple server ticks.
 */
package ol.originallightlib.core.task;
```

---

## `BatchTask<T>`

```java
/**
 * Processes a list of items in batches over multiple server ticks.
 * <p>
 * This is useful for operations that must remain on the main thread but should
 * not be completed in a single tick, such as block placement or large player
 * data updates.
 * </p>
 *
 * @param <T> item type to process
 */
public class BatchTask<T>
```

### Usage

```java
BatchTask.of(items)
    .perTick(100)
    .interval(1L)
    .onEach(item -> process(item))
    .onTick(task -> updateProgress(task.getProgress()))
    .onComplete(() -> done())
    .start(plugin);
```

### Key Methods

```java
public void start()
public void cancel()
public int getProcessedCount()
public int getTotalCount()
public int getRemainingCount()
public double getProgress()
public boolean isComplete()
```

---

## `BatchTaskBuilder<T>`

```java
/**
 * Fluent builder for creating and starting BatchTask instances.
 *
 * @param <T> item type to process
 */
public class BatchTaskBuilder<T>
```

---

# `core.util`

General utilities.

### 建議 package-info.java

```java
/**
 * Provides general-purpose utilities used by the library.
 */
package ol.originallightlib.core.util;
```

---

## `TimeFormatter`

```java
/**
 * Utility class for formatting durations.
 */
public final class TimeFormatter
```

### Common Methods

```java
public static String formatMillis(long millis)
public static String formatSeconds(long seconds)
public static String formatSecondsShort(long seconds)
public static String formatTicks(long ticks)
public static String formatClock(long seconds)
```

### Output Examples

```java
TimeFormatter.formatSeconds(90);      // 1分30秒
TimeFormatter.formatSecondsShort(90); // 1m 30s
TimeFormatter.formatClock(90);        // 01:30
TimeFormatter.formatClock(90000);     // 01:00:00 (+1 Day)
```

---

# `test`

Development and debug commands.

### 建議 package-info.java

```java
/**
 * Contains development and debug commands for testing OriginalLightLib features.
 * <p>
 * These commands should only be registered when debug test commands are enabled.
 * They are not considered part of the public core API.
 * </p>
 */
package ol.originallightlib.test;
```

---

## `TestCommandRegistrar`

```java
/**
 * Registers development and debug subcommands.
 * <p>
 * This registrar should only be called when debug test commands are enabled.
 * </p>
 */
public final class TestCommandRegistrar
```

---

# Recommended Public API Surface

The following packages are intended to be used by dependent plugins:

```text
ol.originallightlib.core.command
ol.originallightlib.core.config
ol.originallightlib.core.cooldown
ol.originallightlib.core.display
ol.originallightlib.core.gui
ol.originallightlib.core.gui.common
ol.originallightlib.core.gui.page
ol.originallightlib.core.gui.selector
ol.originallightlib.core.input
ol.originallightlib.core.item
ol.originallightlib.core.message
ol.originallightlib.core.region
ol.originallightlib.core.task
ol.originallightlib.core.util
```

The following package is development-only:

```text
ol.originallightlib.test
```

---

# Integration Notes for Future Plugins

## Item storage

Dependent plugins should usually create their own `ItemRegistry` instance:

```java
private ItemRegistry itemRegistry;

@Override
public void onEnable() {
    this.itemRegistry = new ItemRegistry(this, "items.yml");
}
```

This stores plugin-specific items in the dependent plugin's data folder instead of OLL's folder.

---

## Selection usage

Dependent plugins can use OLL's global `SelectionManager`:

```java
PlayerSelection selection = OriginalLightLib.getInstance()
    .getSelectionManager()
    .getSelection(player);

CuboidRegion region = selection.toRegion();
```

The dependent plugin decides what to do with the selected region.

---

## Batch task usage

Use `BatchTask` for large main-thread workloads:

```java
BatchTask.of(blocks)
    .perTick(150)
    .interval(1L)
    .onEach(block -> paste(block))
    .onComplete(() -> player.sendMessage("Done"))
    .start(plugin);
```

Do not use async threads for Bukkit world or inventory operations unless the API explicitly supports it.

---

## GUI safety

All GUI icon items are marked internally using `GuiItemMarker`.  
Dependent plugins should not manually remove or depend on this marker.

GUI items are protected from common extraction edge cases such as:

- shift-click
- drag into GUI
- cursor leftovers
- close-click timing issues

---

# Suggested Next Step

When implementing WorldInteraction, start with these OLL APIs:

```text
CommandManager / SubCommand
YamlFile
MessageManager
ItemRegistry
ItemMatcher
ItemSelectGui
SelectionManager
RegionSerializer
BatchTask
ActionBar / BossBar / Sound / Title
CooldownManager
```

Recommended WI implementation order:

```text
1. Basic plugin skeleton
2. WI command root
3. Item registry using OLL ItemRegistry
4. Structure snapshot data model
5. Record selected region
6. Batched paste using BatchTask
7. Event data model
8. Event edit GUI
9. Player interaction trigger
10. Contribution / like system
```


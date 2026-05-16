package ol.originallightlib.core.gui.page;

import ol.originallightlib.core.gui.Gui;
import ol.originallightlib.core.gui.GuiButton;
import ol.originallightlib.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedGui<T> extends Gui {

    private final List<T> items;
    private final int[] contentSlots;

    private int page;

    public PaginatedGui(String title, int size, List<T> items) {
        super(title, size);

        this.items = items == null ? List.of() : new ArrayList<>(items);
        this.contentSlots = defaultContentSlots(size);
        this.page = 0;
    }

    public PaginatedGui(String title, int size, List<T> items, int[] contentSlots) {
        super(title, size);

        this.items = items == null ? List.of() : new ArrayList<>(items);
        this.contentSlots = contentSlots;
        this.page = 0;
    }

    @Override
    protected void draw(Player player) {
        drawContent(player);
        drawNavigation(player);
    }

    private void drawContent(Player player) {
        if (items.isEmpty()) {
            drawEmpty(player);
            return;
        }

        int startIndex = page * contentSlots.length;

        for (int i = 0; i < contentSlots.length; i++) {
            int itemIndex = startIndex + i;

            if (itemIndex >= items.size()) {
                break;
            }

            T item = items.get(itemIndex);
            int slot = contentSlots[i];

            GuiButton button = createItemButton(player, item, itemIndex);

            if (button != null) {
                setButton(slot, button);
            }
        }
    }

    private void drawNavigation(Player player) {
        drawPageInfo();

        if (hasPreviousPage()) {
            setButton(45, new GuiButton(
                    ItemBuilder.of(Material.ARROW)
                            .name("&e上一頁")
                            .lore(
                                    "&7目前頁數：&f" + getDisplayPage() + "&7/&f" + getTotalPages(),
                                    "",
                                    "&e點擊切換到上一頁"
                            )
                            .build(),
                    event -> {
                        previousPage();
                        open(player);
                    }
            ));
        } else {
            setButton(45, new GuiButton(
                    ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE)
                            .name("&8上一頁")
                            .build(),
                    event -> {
                    }
            ));
        }

        if (hasNextPage()) {
            setButton(53, new GuiButton(
                    ItemBuilder.of(Material.ARROW)
                            .name("&e下一頁")
                            .lore(
                                    "&7目前頁數：&f" + getDisplayPage() + "&7/&f" + getTotalPages(),
                                    "",
                                    "&e點擊切換到下一頁"
                            )
                            .build(),
                    event -> {
                        nextPage();
                        open(player);
                    }
            ));
        } else {
            setButton(53, new GuiButton(
                    ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE)
                            .name("&8下一頁")
                            .build(),
                    event -> {
                    }
            ));
        }
    }

    private void drawPageInfo() {
        setButton(49, new GuiButton(
                ItemBuilder.of(Material.PAPER)
                        .name("&f第 &e" + getDisplayPage() + " &f/ &e" + getTotalPages() + " &f頁")
                        .lore(
                                "&7總項目數：&e" + items.size(),
                                "&7每頁項目：&e" + contentSlots.length
                        )
                        .build(),
                event -> {
                }
        ));
    }

    protected void drawEmpty(Player player) {
        setButton(22, new GuiButton(
                ItemBuilder.of(Material.BARRIER)
                        .name("&c沒有可顯示的資料")
                        .lore("&7目前列表是空的。")
                        .build(),
                event -> {
                }
        ));
    }

    protected abstract GuiButton createItemButton(Player player, T item, int index);

    public boolean hasPreviousPage() {
        return page > 0;
    }

    public boolean hasNextPage() {
        return page < getTotalPages() - 1;
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            page--;
        }
    }

    public void nextPage() {
        if (hasNextPage()) {
            page++;
        }
    }

    public int getPage() {
        return page;
    }

    public int getDisplayPage() {
        return page + 1;
    }

    public int getTotalPages() {
        if (items.isEmpty()) {
            return 1;
        }

        return (int) Math.ceil((double) items.size() / contentSlots.length);
    }

    public List<T> getItems() {
        return items;
    }

    public int[] getContentSlots() {
        return contentSlots;
    }

    private int[] defaultContentSlots(int size) {
        if (size != 54) {
            List<Integer> slots = new ArrayList<>();

            for (int slot = 0; slot < size; slot++) {
                slots.add(slot);
            }

            return slots.stream().mapToInt(Integer::intValue).toArray();
        }

        return new int[]{
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43
        };
    }
}
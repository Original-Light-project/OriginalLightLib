package ol.originallightlib.core.gui.common;

import ol.originallightlib.core.gui.Gui;
import ol.originallightlib.core.gui.GuiButton;
import ol.originallightlib.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConfirmGui extends Gui {

    private final List<String> description;

    private final Consumer<Player> confirmAction;
    private final Consumer<Player> cancelAction;
    private final Consumer<Player> backAction;

    private final boolean closeOnConfirm;
    private final boolean closeOnCancel;
    private final boolean closeOnBack;

    private final Material confirmMaterial;
    private final Material cancelMaterial;
    private final Material backMaterial;
    private final Material infoMaterial;
    private final Material fillerMaterial;

    private final String confirmName;
    private final String cancelName;
    private final String backName;
    private final String infoName;

    private final boolean showBackButton;
    private final boolean fillBorder;

    public ConfirmGui(
            String title,
            List<String> description,
            Consumer<Player> confirmAction,
            Consumer<Player> cancelAction
    ) {
        this(
                title,
                description,
                confirmAction,
                cancelAction,
                null,
                true,
                true,
                true,
                Material.LIME_CONCRETE,
                Material.RED_CONCRETE,
                Material.ARROW,
                Material.PAPER,
                Material.GRAY_STAINED_GLASS_PANE,
                "&a確認",
                "&c取消",
                "&e返回",
                "&f操作確認",
                false,
                true
        );
    }

    private ConfirmGui(
            String title,
            List<String> description,
            Consumer<Player> confirmAction,
            Consumer<Player> cancelAction,
            Consumer<Player> backAction,
            boolean closeOnConfirm,
            boolean closeOnCancel,
            boolean closeOnBack,
            Material confirmMaterial,
            Material cancelMaterial,
            Material backMaterial,
            Material infoMaterial,
            Material fillerMaterial,
            String confirmName,
            String cancelName,
            String backName,
            String infoName,
            boolean showBackButton,
            boolean fillBorder
    ) {
        super(title, 27);

        this.description = description == null ? List.of() : description;

        this.confirmAction = confirmAction;
        this.cancelAction = cancelAction;
        this.backAction = backAction;

        this.closeOnConfirm = closeOnConfirm;
        this.closeOnCancel = closeOnCancel;
        this.closeOnBack = closeOnBack;

        this.confirmMaterial = confirmMaterial;
        this.cancelMaterial = cancelMaterial;
        this.backMaterial = backMaterial;
        this.infoMaterial = infoMaterial;
        this.fillerMaterial = fillerMaterial;

        this.confirmName = confirmName;
        this.cancelName = cancelName;
        this.backName = backName;
        this.infoName = infoName;

        this.showBackButton = showBackButton;
        this.fillBorder = fillBorder;
    }

    @Override
    protected void draw(Player player) {
        if (fillBorder) {
            drawBorder();
        }

        setButton(13, new GuiButton(
                ItemBuilder.of(infoMaterial)
                        .name(infoName)
                        .lore(description)
                        .build(),
                event -> {
                    // Info button does not perform any action.
                }
        ));

        setButton(11, new GuiButton(
                ItemBuilder.of(confirmMaterial)
                        .name(confirmName)
                        .lore(
                                "&7點擊後將執行此操作。",
                                "",
                                "&a左鍵確認"
                        )
                        .build(),
                event -> {
                    if (closeOnConfirm) {
                        player.closeInventory();
                    }

                    if (confirmAction != null) {
                        confirmAction.accept(player);
                    }
                }
        ));

        setButton(15, new GuiButton(
                ItemBuilder.of(cancelMaterial)
                        .name(cancelName)
                        .lore(
                                "&7點擊後將取消此操作。",
                                "",
                                "&c左鍵取消"
                        )
                        .build(),
                event -> {
                    if (closeOnCancel) {
                        player.closeInventory();
                    }

                    if (cancelAction != null) {
                        cancelAction.accept(player);
                    }
                }
        ));

        if (showBackButton) {
            setButton(22, new GuiButton(
                    ItemBuilder.of(backMaterial)
                            .name(backName)
                            .lore(
                                    "&7返回上一個畫面。",
                                    "",
                                    "&e點擊返回"
                            )
                            .build(),
                    event -> {
                        if (closeOnBack) {
                            player.closeInventory();
                        }

                        if (backAction != null) {
                            backAction.accept(player);
                        }
                    }
            ));
        }
    }

    private void drawBorder() {
        int[] borderSlots = {
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17,
                18, 19, 20, 21, 23, 24, 25, 26
        };

        for (int slot : borderSlots) {
            setButton(slot, new GuiButton(
                    ItemBuilder.of(fillerMaterial)
                            .name("&f")
                            .build(),
                    event -> {
                    }
            ));
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String title = "§8確認操作";
        private List<String> description = new ArrayList<>();

        private Consumer<Player> confirmAction;
        private Consumer<Player> cancelAction;
        private Consumer<Player> backAction;

        private boolean closeOnConfirm = true;
        private boolean closeOnCancel = true;
        private boolean closeOnBack = true;

        private Material confirmMaterial = Material.LIME_CONCRETE;
        private Material cancelMaterial = Material.RED_CONCRETE;
        private Material backMaterial = Material.ARROW;
        private Material infoMaterial = Material.PAPER;
        private Material fillerMaterial = Material.GRAY_STAINED_GLASS_PANE;

        private String confirmName = "&a確認";
        private String cancelName = "&c取消";
        private String backName = "&e返回";
        private String infoName = "&f操作確認";

        private boolean showBackButton = false;
        private boolean fillBorder = true;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String... description) {
            this.description = Arrays.asList(description);
            return this;
        }

        public Builder description(List<String> description) {
            this.description = description == null ? List.of() : description;
            return this;
        }

        public Builder onConfirm(Consumer<Player> confirmAction) {
            this.confirmAction = confirmAction;
            return this;
        }

        public Builder onCancel(Consumer<Player> cancelAction) {
            this.cancelAction = cancelAction;
            return this;
        }

        public Builder onBack(Consumer<Player> backAction) {
            this.backAction = backAction;
            this.showBackButton = true;
            return this;
        }

        public Builder closeOnConfirm(boolean closeOnConfirm) {
            this.closeOnConfirm = closeOnConfirm;
            return this;
        }

        public Builder closeOnCancel(boolean closeOnCancel) {
            this.closeOnCancel = closeOnCancel;
            return this;
        }

        public Builder closeOnBack(boolean closeOnBack) {
            this.closeOnBack = closeOnBack;
            return this;
        }

        public Builder confirmItem(Material material, String name) {
            this.confirmMaterial = material;
            this.confirmName = name;
            return this;
        }

        public Builder cancelItem(Material material, String name) {
            this.cancelMaterial = material;
            this.cancelName = name;
            return this;
        }

        public Builder backItem(Material material, String name) {
            this.backMaterial = material;
            this.backName = name;
            this.showBackButton = true;
            return this;
        }

        public Builder infoItem(Material material, String name) {
            this.infoMaterial = material;
            this.infoName = name;
            return this;
        }

        public Builder fillerItem(Material material) {
            this.fillerMaterial = material;
            return this;
        }

        public Builder showBackButton(boolean showBackButton) {
            this.showBackButton = showBackButton;
            return this;
        }

        public Builder fillBorder(boolean fillBorder) {
            this.fillBorder = fillBorder;
            return this;
        }

        public ConfirmGui build() {
            return new ConfirmGui(
                    title,
                    description,
                    confirmAction,
                    cancelAction,
                    backAction,
                    closeOnConfirm,
                    closeOnCancel,
                    closeOnBack,
                    confirmMaterial,
                    cancelMaterial,
                    backMaterial,
                    infoMaterial,
                    fillerMaterial,
                    confirmName,
                    cancelName,
                    backName,
                    infoName,
                    showBackButton,
                    fillBorder
            );
        }

        public void open(Player player) {
            build().open(player);
        }
    }
}
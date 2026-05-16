package ol.originallightlib.core.region;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public final class RegionSerializer {

    private RegionSerializer() {
    }

    public static void saveBlockPosition(FileConfiguration config, String path, BlockPosition position) {
        if (config == null || path == null || path.isBlank()) {
            return;
        }

        if (position == null) {
            config.set(path, null);
            return;
        }

        config.set(path + ".world", position.getWorldName());
        config.set(path + ".x", position.getX());
        config.set(path + ".y", position.getY());
        config.set(path + ".z", position.getZ());
    }

    public static BlockPosition loadBlockPosition(FileConfiguration config, String path) {
        if (config == null || path == null || path.isBlank()) {
            return null;
        }

        if (!config.contains(path)) {
            return null;
        }

        String worldName = config.getString(path + ".world");

        if (worldName == null || worldName.isBlank()) {
            return null;
        }

        int x = config.getInt(path + ".x");
        int y = config.getInt(path + ".y");
        int z = config.getInt(path + ".z");

        return new BlockPosition(worldName, x, y, z);
    }

    public static void saveCuboidRegion(FileConfiguration config, String path, CuboidRegion region) {
        if (config == null || path == null || path.isBlank()) {
            return;
        }

        if (region == null) {
            config.set(path, null);
            return;
        }

        config.set(path + ".world", region.getWorldName());

        config.set(path + ".min-x", region.getMinX());
        config.set(path + ".max-x", region.getMaxX());

        config.set(path + ".min-y", region.getMinY());
        config.set(path + ".max-y", region.getMaxY());

        config.set(path + ".min-z", region.getMinZ());
        config.set(path + ".max-z", region.getMaxZ());
    }

    public static CuboidRegion loadCuboidRegion(FileConfiguration config, String path) {
        if (config == null || path == null || path.isBlank()) {
            return null;
        }

        if (!config.contains(path)) {
            return null;
        }

        String worldName = config.getString(path + ".world");

        if (worldName == null || worldName.isBlank()) {
            return null;
        }

        int minX = config.getInt(path + ".min-x");
        int maxX = config.getInt(path + ".max-x");

        int minY = config.getInt(path + ".min-y");
        int maxY = config.getInt(path + ".max-y");

        int minZ = config.getInt(path + ".min-z");
        int maxZ = config.getInt(path + ".max-z");

        BlockPosition pos1 = new BlockPosition(worldName, minX, minY, minZ);
        BlockPosition pos2 = new BlockPosition(worldName, maxX, maxY, maxZ);

        return new CuboidRegion(pos1, pos2);
    }

    public static void savePlayerSelection(FileConfiguration config, String path, PlayerSelection selection) {
        if (config == null || path == null || path.isBlank()) {
            return;
        }

        if (selection == null) {
            config.set(path, null);
            return;
        }

        saveBlockPosition(config, path + ".pos1", selection.getPos1());
        saveBlockPosition(config, path + ".pos2", selection.getPos2());

        if (selection.isComplete() && selection.isSameWorld()) {
            saveCuboidRegion(config, path + ".region", selection.toRegion());
        } else {
            config.set(path + ".region", null);
        }
    }

    public static PlayerSelection loadPlayerSelection(FileConfiguration config, String path) {
        if (config == null || path == null || path.isBlank()) {
            return null;
        }

        if (!config.contains(path)) {
            return null;
        }

        BlockPosition pos1 = loadBlockPosition(config, path + ".pos1");
        BlockPosition pos2 = loadBlockPosition(config, path + ".pos2");

        PlayerSelection selection = new PlayerSelection();
        selection.setPos1(pos1);
        selection.setPos2(pos2);

        return selection;
    }

    public static boolean isValidBlockPositionSection(ConfigurationSection section) {
        if (section == null) {
            return false;
        }

        return section.contains("world")
                && section.contains("x")
                && section.contains("y")
                && section.contains("z");
    }

    public static boolean isValidRegionSection(ConfigurationSection section) {
        if (section == null) {
            return false;
        }

        return section.contains("world")
                && section.contains("min-x")
                && section.contains("max-x")
                && section.contains("min-y")
                && section.contains("max-y")
                && section.contains("min-z")
                && section.contains("max-z");
    }
}
package ol.originallightlib.core.region;

import org.bukkit.Location;
import org.bukkit.World;

public class CuboidRegion {

    private final String worldName;

    private final int minX;
    private final int maxX;

    private final int minY;
    private final int maxY;

    private final int minZ;
    private final int maxZ;

    public CuboidRegion(BlockPosition pos1, BlockPosition pos2) {
        if (!pos1.getWorldName().equals(pos2.getWorldName())) {
            throw new IllegalArgumentException("兩個選區點必須在同一個世界。");
        }

        this.worldName = pos1.getWorldName();

        this.minX = Math.min(pos1.getX(), pos2.getX());
        this.maxX = Math.max(pos1.getX(), pos2.getX());

        this.minY = Math.min(pos1.getY(), pos2.getY());
        this.maxY = Math.max(pos1.getY(), pos2.getY());

        this.minZ = Math.min(pos1.getZ(), pos2.getZ());
        this.maxZ = Math.max(pos1.getZ(), pos2.getZ());
    }

    public boolean contains(Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        if (!location.getWorld().getName().equals(worldName)) {
            return false;
        }

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return x >= minX && x <= maxX
                && y >= minY && y <= maxY
                && z >= minZ && z <= maxZ;
    }

    public long getVolume() {
        long width = maxX - minX + 1L;
        long height = maxY - minY + 1L;
        long length = maxZ - minZ + 1L;

        return width * height * length;
    }

    public String getWorldName() {
        return worldName;
    }

    public World getWorld() {
        return org.bukkit.Bukkit.getWorld(worldName);
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public String toDisplayString() {
        return "world=" + worldName
                + ", x=" + minX + "~" + maxX
                + ", y=" + minY + "~" + maxY
                + ", z=" + minZ + "~" + maxZ
                + ", volume=" + getVolume();
    }
}
package ol.originallightlib.core.region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class BlockPosition {

    private final String worldName;
    private final int x;
    private final int y;
    private final int z;

    public BlockPosition(String worldName, int x, int y, int z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static BlockPosition fromLocation(Location location) {
        return new BlockPosition(
                location.getWorld().getName(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }

    public Location toLocation() {
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return null;
        }

        return new Location(world, x, y, z);
    }

    public String getWorldName() {
        return worldName;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String toDisplayString() {
        return "world=" + worldName + ", x=" + x + ", y=" + y + ", z=" + z;
    }
}
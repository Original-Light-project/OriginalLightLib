package ol.originallightlib.core.region;

public class PlayerSelection {

    private BlockPosition pos1;
    private BlockPosition pos2;

    public BlockPosition getPos1() {
        return pos1;
    }

    public void setPos1(BlockPosition pos1) {
        this.pos1 = pos1;
    }

    public BlockPosition getPos2() {
        return pos2;
    }

    public void setPos2(BlockPosition pos2) {
        this.pos2 = pos2;
    }

    public boolean hasPos1() {
        return pos1 != null;
    }

    public boolean hasPos2() {
        return pos2 != null;
    }

    public boolean isComplete() {
        return pos1 != null && pos2 != null;
    }

    public boolean isSameWorld() {
        if (!isComplete()) {
            return false;
        }

        return pos1.getWorldName().equals(pos2.getWorldName());
    }

    public CuboidRegion toRegion() {
        if (!isComplete()) {
            return null;
        }

        if (!isSameWorld()) {
            return null;
        }

        return new CuboidRegion(pos1, pos2);
    }

    public void clear() {
        this.pos1 = null;
        this.pos2 = null;
    }
}
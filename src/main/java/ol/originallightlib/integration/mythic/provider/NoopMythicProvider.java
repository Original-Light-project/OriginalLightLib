package ol.originallightlib.integration.mythic.provider;

import ol.originallightlib.integration.mythic.MythicProvider;
import ol.originallightlib.integration.mythic.MythicResult;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Optional;

public class NoopMythicProvider implements MythicProvider {

    private static final String UNAVAILABLE = "MythicMobs is not available.";

    @Override
    public String getName() {
        return "none";
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean hasMob(String mobId) {
        return false;
    }

    @Override
    public Optional<String> resolveMobId(Entity entity) {
        return Optional.empty();
    }

    @Override
    public MythicResult spawnMob(String mobId, Location location, int amount, double level) {
        return MythicResult.failure(UNAVAILABLE);
    }

    @Override
    public boolean hasItem(String itemId) {
        return false;
    }

    @Override
    public Optional<ItemStack> createItem(String itemId, int amount) {
        return Optional.empty();
    }

    @Override
    public MythicResult giveItem(Player player, String itemId, int amount) {
        return MythicResult.failure(UNAVAILABLE);
    }

    @Override
    public boolean hasSkill(String skillId) {
        return false;
    }

    @Override
    public MythicResult castSkill(String skillId, Entity caster, Collection<Entity> entityTargets, Collection<Location> locationTargets, float power) {
        return MythicResult.failure(UNAVAILABLE);
    }
}

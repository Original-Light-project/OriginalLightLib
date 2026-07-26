package ol.originallightlib.integration.mythic;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class MythicService {

    private final MythicProvider provider;

    public MythicService(MythicProvider provider) {
        this.provider = provider;
    }

    public String getProviderName() {
        return provider.getName();
    }

    public boolean isAvailable() {
        return provider.isAvailable();
    }

    public boolean hasMob(String mobId) {
        return provider.hasMob(mobId);
    }

    public Optional<String> resolveMobId(Entity entity) {
        return entity == null ? Optional.empty() : provider.resolveMobId(entity);
    }

    public MythicResult spawnMob(String mobId, Location location, int amount) {
        return provider.spawnMob(mobId, location, amount, 1D);
    }

    public MythicResult spawnMob(String mobId, Location location, int amount, double level) {
        return provider.spawnMob(mobId, location, amount, level);
    }

    public boolean hasItem(String itemId) {
        return provider.hasItem(itemId);
    }

    public Optional<ItemStack> createItem(String itemId, int amount) {
        return provider.createItem(itemId, amount);
    }

    public MythicResult giveItem(Player player, String itemId, int amount) {
        return provider.giveItem(player, itemId, amount);
    }

    public boolean hasSkill(String skillId) {
        return provider.hasSkill(skillId);
    }

    public MythicResult castSkill(String skillId, Entity caster) {
        return provider.castSkill(skillId, caster, Collections.emptyList(), Collections.emptyList(), 1F);
    }

    public MythicResult castSkill(String skillId, Entity caster, Collection<Entity> entityTargets, Collection<Location> locationTargets, float power) {
        return provider.castSkill(skillId, caster, entityTargets, locationTargets, power);
    }
}

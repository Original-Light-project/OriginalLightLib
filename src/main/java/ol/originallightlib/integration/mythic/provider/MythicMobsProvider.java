package ol.originallightlib.integration.mythic.provider;

import io.lumine.mythic.api.MythicPlugin;
import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.adapters.AbstractItemStack;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import ol.originallightlib.integration.mythic.MythicResult;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MythicMobsProvider implements ol.originallightlib.integration.mythic.MythicProvider {

    @Override
    public String getName() {
        return "MythicMobs";
    }

    @Override
    public boolean isAvailable() {
        MythicPlugin plugin = MythicProvider.get();
        return plugin != null && !plugin.isLoading() && !plugin.isShuttingDown();
    }

    @Override
    public boolean hasMob(String mobId) {
        if (!isAvailable()) {
            return false;
        }
        return MythicProvider.get().getMobManager().getMythicMob(mobId).isPresent();
    }

    @Override
    public Optional<String> resolveMobId(Entity entity) {
        if (!isAvailable() || entity == null) {
            return Optional.empty();
        }
        return MythicBukkit.inst().getMobManager().getMythicType(entity);
    }

    @Override
    public MythicResult spawnMob(String mobId, Location location, int amount, double level) {
        if (!isAvailable()) {
            return MythicResult.failure("MythicMobs is not available.");
        }
        if (location == null || location.getWorld() == null) {
            return MythicResult.failure("Spawn location is invalid.");
        }
        if (amount <= 0) {
            return MythicResult.failure("Spawn amount must be greater than zero.");
        }

        Optional<io.lumine.mythic.api.mobs.MythicMob> mob = MythicProvider.get().getMobManager().getMythicMob(mobId);
        if (mob.isEmpty()) {
            return MythicResult.failure("Unknown MythicMob: " + mobId);
        }

        for (int index = 0; index < amount; index++) {
            mob.get().spawn(BukkitAdapter.adapt(location), level);
        }

        return MythicResult.success();
    }

    @Override
    public boolean hasItem(String itemId) {
        if (!isAvailable()) {
            return false;
        }
        return MythicProvider.get().getItemManager().getItem(itemId).isPresent();
    }

    @Override
    public Optional<ItemStack> createItem(String itemId, int amount) {
        if (!isAvailable() || amount <= 0) {
            return Optional.empty();
        }

        Optional<MythicItem> item = MythicProvider.get().getItemManager().getItem(itemId);
        if (item.isEmpty()) {
            return Optional.empty();
        }

        AbstractItemStack generated = item.get().generateItemStack(amount);
        return Optional.of(BukkitAdapter.adapt(generated));
    }

    @Override
    public MythicResult giveItem(Player player, String itemId, int amount) {
        if (player == null) {
            return MythicResult.failure("Player is invalid.");
        }

        Optional<ItemStack> item = createItem(itemId, amount);
        if (item.isEmpty()) {
            return MythicResult.failure("Unknown MythicItem: " + itemId);
        }

        Map<Integer, ItemStack> leftovers = player.getInventory().addItem(item.get());
        if (!leftovers.isEmpty()) {
            leftovers.values().forEach(leftover -> player.getWorld().dropItemNaturally(player.getLocation(), leftover));
        }

        return MythicResult.success();
    }

    @Override
    public boolean hasSkill(String skillId) {
        if (!isAvailable()) {
            return false;
        }
        return MythicProvider.get().getSkillManager().getSkill(skillId).isPresent();
    }

    @Override
    public MythicResult castSkill(String skillId, Entity caster, Collection<Entity> entityTargets, Collection<Location> locationTargets, float power) {
        if (!isAvailable()) {
            return MythicResult.failure("MythicMobs is not available.");
        }
        if (caster == null) {
            return MythicResult.failure("Skill caster is invalid.");
        }
        if (!hasSkill(skillId)) {
            return MythicResult.failure("Unknown Mythic skill: " + skillId);
        }

        BukkitAPIHelper helper = MythicBukkit.inst().getAPIHelper();
        boolean success = helper.castSkill(
                caster,
                skillId,
                null,
                caster.getLocation(),
                entityTargets,
                locationTargets,
                power
        );

        return success ? MythicResult.success() : MythicResult.failure("Mythic skill cast failed: " + skillId);
    }
}

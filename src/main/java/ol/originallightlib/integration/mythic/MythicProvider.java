package ol.originallightlib.integration.mythic;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Optional;

public interface MythicProvider {

    String getName();

    boolean isAvailable();

    boolean hasMob(String mobId);

    MythicResult spawnMob(String mobId, Location location, int amount, double level);

    boolean hasItem(String itemId);

    Optional<ItemStack> createItem(String itemId, int amount);

    MythicResult giveItem(Player player, String itemId, int amount);

    boolean hasSkill(String skillId);

    MythicResult castSkill(String skillId, Entity caster, Collection<Entity> entityTargets, Collection<Location> locationTargets, float power);
}

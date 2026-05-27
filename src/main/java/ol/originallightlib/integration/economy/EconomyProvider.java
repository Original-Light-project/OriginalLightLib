package ol.originallightlib.integration.economy;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EconomyProvider {

    String getName();

    boolean isAvailable();

    boolean hasCurrency(String currencyId);

    Collection<CurrencyInfo> getCurrencies();

    Optional<CurrencyInfo> getCurrency(String currencyId);

    CompletableFuture<EconomyResult> getBalance(UUID playerId, String currencyId);

    EconomyResult getBalance(Player player, String currencyId);

    CompletableFuture<EconomyResult> deposit(UUID playerId, String currencyId, double amount);

    EconomyResult deposit(Player player, String currencyId, double amount);

    CompletableFuture<EconomyResult> withdraw(UUID playerId, String currencyId, double amount);

    EconomyResult withdraw(Player player, String currencyId, double amount);

    CompletableFuture<EconomyResult> setBalance(UUID playerId, String currencyId, double amount);

    EconomyResult setBalance(Player player, String currencyId, double amount);
}

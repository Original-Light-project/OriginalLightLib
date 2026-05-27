package ol.originallightlib.integration.economy.provider;

import ol.originallightlib.integration.economy.CurrencyInfo;
import ol.originallightlib.integration.economy.EconomyProvider;
import ol.originallightlib.integration.economy.EconomyResult;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class NoopEconomyProvider implements EconomyProvider {

    private static final String UNAVAILABLE = "No economy provider is available.";

    @Override
    public String getName() {
        return "none";
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean hasCurrency(String currencyId) {
        return false;
    }

    @Override
    public Collection<CurrencyInfo> getCurrencies() {
        return Collections.emptyList();
    }

    @Override
    public Optional<CurrencyInfo> getCurrency(String currencyId) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<EconomyResult> getBalance(UUID playerId, String currencyId) {
        return CompletableFuture.completedFuture(EconomyResult.failure(UNAVAILABLE));
    }

    @Override
    public EconomyResult getBalance(Player player, String currencyId) {
        return EconomyResult.failure(UNAVAILABLE);
    }

    @Override
    public CompletableFuture<EconomyResult> deposit(UUID playerId, String currencyId, double amount) {
        return CompletableFuture.completedFuture(EconomyResult.failure(UNAVAILABLE));
    }

    @Override
    public EconomyResult deposit(Player player, String currencyId, double amount) {
        return EconomyResult.failure(UNAVAILABLE);
    }

    @Override
    public CompletableFuture<EconomyResult> withdraw(UUID playerId, String currencyId, double amount) {
        return CompletableFuture.completedFuture(EconomyResult.failure(UNAVAILABLE));
    }

    @Override
    public EconomyResult withdraw(Player player, String currencyId, double amount) {
        return EconomyResult.failure(UNAVAILABLE);
    }

    @Override
    public CompletableFuture<EconomyResult> setBalance(UUID playerId, String currencyId, double amount) {
        return CompletableFuture.completedFuture(EconomyResult.failure(UNAVAILABLE));
    }

    @Override
    public EconomyResult setBalance(Player player, String currencyId, double amount) {
        return EconomyResult.failure(UNAVAILABLE);
    }
}

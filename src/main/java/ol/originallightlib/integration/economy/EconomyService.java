package ol.originallightlib.integration.economy;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EconomyService {

    private final EconomyProvider provider;

    public EconomyService(EconomyProvider provider) {
        this.provider = provider;
    }

    public String getProviderName() {
        return provider.getName();
    }

    public boolean isAvailable() {
        return provider.isAvailable();
    }

    public boolean hasCurrency(String currencyId) {
        return provider.hasCurrency(currencyId);
    }

    public Collection<CurrencyInfo> getCurrencies() {
        return provider.getCurrencies();
    }

    public Optional<CurrencyInfo> getCurrency(String currencyId) {
        return provider.getCurrency(currencyId);
    }

    public CompletableFuture<EconomyResult> getBalance(UUID playerId, String currencyId) {
        return provider.getBalance(playerId, currencyId);
    }

    public EconomyResult getBalance(Player player, String currencyId) {
        return provider.getBalance(player, currencyId);
    }

    public CompletableFuture<EconomyResult> deposit(UUID playerId, String currencyId, double amount) {
        return provider.deposit(playerId, currencyId, amount);
    }

    public EconomyResult deposit(Player player, String currencyId, double amount) {
        return provider.deposit(player, currencyId, amount);
    }

    public CompletableFuture<EconomyResult> withdraw(UUID playerId, String currencyId, double amount) {
        return provider.withdraw(playerId, currencyId, amount);
    }

    public EconomyResult withdraw(Player player, String currencyId, double amount) {
        return provider.withdraw(player, currencyId, amount);
    }

    public CompletableFuture<EconomyResult> setBalance(UUID playerId, String currencyId, double amount) {
        return provider.setBalance(playerId, currencyId, amount);
    }

    public EconomyResult setBalance(Player player, String currencyId, double amount) {
        return provider.setBalance(player, currencyId, amount);
    }
}

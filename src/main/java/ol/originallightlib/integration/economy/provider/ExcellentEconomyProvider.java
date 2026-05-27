package ol.originallightlib.integration.economy.provider;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.integration.economy.CurrencyInfo;
import ol.originallightlib.integration.economy.EconomyProvider;
import ol.originallightlib.integration.economy.EconomyResult;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class ExcellentEconomyProvider implements EconomyProvider {

    private final OriginalLightLib plugin;
    private final Object api;

    public ExcellentEconomyProvider(OriginalLightLib plugin) {
        this.plugin = plugin;
        this.api = resolveApi();
    }

    @Override
    public String getName() {
        return "ExcellentEconomy";
    }

    @Override
    public boolean isAvailable() {
        return api != null && canPerformOperations();
    }

    @Override
    public boolean hasCurrency(String currencyId) {
        if (api == null) {
            return false;
        }
        Object result = invoke(api, "hasCurrency", new Class<?>[]{String.class}, currencyId);
        return result instanceof Boolean value && value;
    }

    @Override
    public Collection<CurrencyInfo> getCurrencies() {
        if (api == null) {
            return Collections.emptyList();
        }

        Object result = invoke(api, "getCurrencies", new Class<?>[0]);
        if (!(result instanceof Collection<?> currencies)) {
            return Collections.emptyList();
        }

        Collection<CurrencyInfo> infos = new ArrayList<>();
        for (Object currency : currencies) {
            readCurrencyInfo(currency).ifPresent(infos::add);
        }
        return infos;
    }

    @Override
    public Optional<CurrencyInfo> getCurrency(String currencyId) {
        if (api == null) {
            return Optional.empty();
        }

        Object result = invoke(api, "currencyById", new Class<?>[]{String.class}, currencyId);
        if (result instanceof Optional<?> optional && optional.isPresent()) {
            return readCurrencyInfo(optional.get());
        }
        return Optional.empty();
    }

    @Override
    public CompletableFuture<EconomyResult> getBalance(UUID playerId, String currencyId) {
        if (!isAvailable()) {
            return CompletableFuture.completedFuture(unavailable());
        }

        Object result = invoke(api, "getBalanceAsync", new Class<?>[]{UUID.class, String.class}, playerId, currencyId);
        if (!(result instanceof CompletableFuture<?> future)) {
            return CompletableFuture.completedFuture(EconomyResult.failure("ExcellentEconomy balance API call failed."));
        }

        return future.thenApply(value -> {
            if (value instanceof Number number) {
                return EconomyResult.success(currencyId, number.doubleValue());
            }
            return EconomyResult.failure("ExcellentEconomy returned an invalid balance value.");
        });
    }

    @Override
    public EconomyResult getBalance(Player player, String currencyId) {
        if (!isAvailable()) {
            return unavailable();
        }

        Object result = invoke(api, "getBalance", new Class<?>[]{Player.class, String.class}, player, currencyId);
        if (result instanceof Number number) {
            return EconomyResult.success(currencyId, number.doubleValue());
        }
        return EconomyResult.failure("ExcellentEconomy balance API call failed.");
    }

    @Override
    public CompletableFuture<EconomyResult> deposit(UUID playerId, String currencyId, double amount) {
        return operationAsync("depositAsync", playerId, currencyId, amount);
    }

    @Override
    public EconomyResult deposit(Player player, String currencyId, double amount) {
        return operation("deposit", player, currencyId, amount);
    }

    @Override
    public CompletableFuture<EconomyResult> withdraw(UUID playerId, String currencyId, double amount) {
        return operationAsync("withdrawAsync", playerId, currencyId, amount);
    }

    @Override
    public EconomyResult withdraw(Player player, String currencyId, double amount) {
        return operation("withdraw", player, currencyId, amount);
    }

    @Override
    public CompletableFuture<EconomyResult> setBalance(UUID playerId, String currencyId, double amount) {
        return operationAsync("setBalanceAsync", playerId, currencyId, amount);
    }

    @Override
    public EconomyResult setBalance(Player player, String currencyId, double amount) {
        return operation("setBalance", player, currencyId, amount);
    }

    private CompletableFuture<EconomyResult> operationAsync(String method, UUID playerId, String currencyId, double amount) {
        if (!isAvailable()) {
            return CompletableFuture.completedFuture(unavailable());
        }

        Object result = invoke(api, method, new Class<?>[]{UUID.class, String.class, double.class}, playerId, currencyId, amount);
        if (!(result instanceof CompletableFuture<?> future)) {
            return CompletableFuture.completedFuture(EconomyResult.failure("ExcellentEconomy operation API call failed: " + method));
        }

        return future.thenApply(value -> operationResult(value, currencyId, amount));
    }

    private EconomyResult operation(String method, Player player, String currencyId, double amount) {
        if (!isAvailable()) {
            return unavailable();
        }

        Object result = invoke(api, method, new Class<?>[]{Player.class, String.class, double.class}, player, currencyId, amount);
        if (result instanceof Boolean value && value) {
            return EconomyResult.success(currencyId, amount);
        }
        return EconomyResult.failure("ExcellentEconomy operation failed: " + method);
    }

    private EconomyResult operationResult(Object result, String currencyId, double amount) {
        Object success = invoke(result, "success", new Class<?>[0]);
        if (success instanceof Boolean value && value) {
            return EconomyResult.success(currencyId, amount);
        }
        return EconomyResult.failure("ExcellentEconomy operation failed.");
    }

    private EconomyResult unavailable() {
        return EconomyResult.failure("ExcellentEconomy is not available or is not ready for operations.");
    }

    private boolean canPerformOperations() {
        Object result = invoke(api, "canPerformOperations", new Class<?>[0]);
        return result instanceof Boolean value && value;
    }

    private Optional<CurrencyInfo> readCurrencyInfo(Object currency) {
        if (currency == null) {
            return Optional.empty();
        }

        Object id = invoke(currency, "getId", new Class<?>[0]);
        Object name = invoke(currency, "getName", new Class<?>[0]);
        Object symbol = invoke(currency, "getSymbol", new Class<?>[0]);
        Object decimal = invoke(currency, "isDecimal", new Class<?>[0]);

        if (!(id instanceof String currencyId)) {
            return Optional.empty();
        }

        return Optional.of(new CurrencyInfo(
                currencyId,
                name instanceof String value ? value : currencyId,
                symbol instanceof String value ? value : "",
                decimal instanceof Boolean value && value
        ));
    }

    private Object resolveApi() {
        Plugin externalPlugin = Bukkit.getPluginManager().getPlugin("ExcellentEconomy");
        if (externalPlugin == null) {
            return null;
        }

        Object result = invoke(externalPlugin, "getAPI", new Class<?>[0]);
        if (result == null) {
            plugin.getLogger().warning("ExcellentEconomy was found, but getAPI() returned null.");
        }
        return result;
    }

    private Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object... args) {
        if (target == null) {
            return null;
        }

        try {
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(target, args);
        } catch (ReflectiveOperationException exception) {
            plugin.getLogger().log(Level.WARNING, "Failed to invoke ExcellentEconomy API method: " + methodName, exception);
            return null;
        }
    }
}

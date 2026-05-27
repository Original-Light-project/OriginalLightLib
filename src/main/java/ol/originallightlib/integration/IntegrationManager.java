package ol.originallightlib.integration;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.integration.economy.EconomyService;
import ol.originallightlib.integration.economy.provider.ExcellentEconomyProvider;
import ol.originallightlib.integration.economy.provider.NoopEconomyProvider;
import ol.originallightlib.integration.mythic.MythicService;
import ol.originallightlib.integration.mythic.provider.MythicMobsProvider;
import ol.originallightlib.integration.mythic.provider.NoopMythicProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class IntegrationManager {

    private final OriginalLightLib plugin;
    private EconomyService economyService;
    private MythicService mythicService;

    public IntegrationManager(OriginalLightLib plugin) {
        this.plugin = plugin;
    }

    public void load() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        if (pluginManager.isPluginEnabled("ExcellentEconomy")) {
            this.economyService = new EconomyService(new ExcellentEconomyProvider(plugin));
        } else {
            this.economyService = new EconomyService(new NoopEconomyProvider());
        }

        if (pluginManager.isPluginEnabled("MythicMobs")) {
            this.mythicService = new MythicService(new MythicMobsProvider());
        } else {
            this.mythicService = new MythicService(new NoopMythicProvider());
        }

        plugin.getLogger().info("Economy provider: " + economyService.getProviderName());
        plugin.getLogger().info("Mythic provider: " + mythicService.getProviderName());
    }

    public EconomyService economy() {
        return economyService;
    }

    public MythicService mythic() {
        return mythicService;
    }
}

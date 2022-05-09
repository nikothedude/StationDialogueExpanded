package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.CoreCampaignPluginImpl;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;

public class SDE_ColonyMarketHelper extends CoreCampaignPluginImpl {

    @Override
    public void updateMarketFacts(MarketAPI market, MemoryAPI memory) {
        if (market.getMemoryWithoutUpdate().getBoolean("$marketSuspicionBlocked")) {
            memory.set(MemFlags.MEMORY_MARKET_SMUGGLING_SUSPICION_LEVEL, 0, 0);
        }
    }
}

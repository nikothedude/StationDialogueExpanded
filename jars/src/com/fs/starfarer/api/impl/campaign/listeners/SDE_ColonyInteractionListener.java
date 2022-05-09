package com.fs.starfarer.api.impl.campaign.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.PlayerMarketTransaction;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.ColonyInteractionListener;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.shared.PlayerTradeDataForSubmarket;

import static com.fs.starfarer.api.impl.campaign.ids.MemFlags.MEMORY_MARKET_SMUGGLING_SUSPICION_LEVEL;

public class SDE_ColonyInteractionListener extends BaseCampaignEventListener {

    public SDE_ColonyInteractionListener(boolean permaRegister) {
        super(permaRegister);
    }

    @Override
    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {
        MarketAPI market = transaction.getMarket();
        MemoryAPI memory = market.getMemoryWithoutUpdate();

        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            SubmarketAPI blackmarket = market.getSubmarket(Submarkets.SUBMARKET_BLACK);
            if (memory.getBoolean("$marketSuspicionBlocked")) {
                if (transaction.getSubmarket().getSpecId().equals("black_market")) {
                    PlayerTradeDataForSubmarket blackmarket_data = new PlayerTradeDataForSubmarket(blackmarket);
                    blackmarket_data.setTotalPlayerTradeValue(0f); //TODO: dont make this wipe suspicion, save the old suspicion
                }
            }
        }
    }
}

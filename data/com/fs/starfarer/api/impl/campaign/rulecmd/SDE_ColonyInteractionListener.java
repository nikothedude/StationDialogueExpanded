package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlayerMarketTransaction;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.ColonyInteractionListener;

public class SDE_ColonyInteractionListener implements ColonyInteractionListener {

    public SDE_ColonyInteractionListener(MarketAPI market) {
        Global.getSector().getListenerManager().addListener(this);
    }

    @Override
    public void reportPlayerOpenedMarket(MarketAPI market) {}

    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {
        if (market.getMemoryWithoutUpdate().getBoolean("$marketSuspicionBlocked")) {
            if (market.getMemoryWithoutUpdate().getFloat("$userTransponderSetting") != 0f) {
                float userSetting = Float.parseFloat("$userTransponderSetting");
                Global.getSettings().setFloat("transponderOffMarketAwarenessMult", userSetting);
            }
            if (Global.getSector().getPlayerFleet().isTransponderOn()) {
                Global.getSector().getCampaignUI().addMessage("Transponder on after suspicionblockedmarket exited, this shouldnt happen - SDE");
            }
            if (market.getMemoryWithoutUpdate().getBoolean("$userTransponder")) {
                Global.getSector().getPlayerFleet().setTransponderOn(true);
            }
            else if (!market.getMemoryWithoutUpdate().getBoolean("$userTransponder")) {
                Global.getSector().getCampaignUI().addMessage("$userTransponder was FALSE. why.");
                Global.getSector().getPlayerFleet().setTransponderOn(false);
            }
            Global.getSector().getListenerManager().removeListener(this);
            Global.getSector().getCampaignUI().addMessage("reportplayer called");
        }
    }

    @Override
    public void reportPlayerOpenedMarketAndCargoUpdated(MarketAPI market) {}

    @Override
    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {}
}

package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class SDE_blindEyeClass extends BaseCommandPlugin {

    private static final String TRANSPONDERSETTING = "$transponderSetting";

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        if (dialog.getInteractionTarget().getMarket() == null) {
            return false;
        }

        MarketAPI market = dialog.getInteractionTarget().getMarket();
        MemoryAPI markmem = market.getMemory();
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            blockSuspicion(market, markmem); //make it possible for backstabbing
            return true;
        }
        return false;
    }

    public static void blockSuspicion(MarketAPI market, MemoryAPI memory) {
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            Global.getSector().getPlayerFleet().setTransponderOn(false);
            float transponderSetting = Global.getSettings().getFloat("transponderOffMarketAwarenessMult");
            if (transponderSetting != 0f) {
                Global.getSettings().setFloat("transponderOffMarketAwarenessMult", 0f);
            }
            Global.getSector().getListenerManager().addListener(market);
        }
    }
}

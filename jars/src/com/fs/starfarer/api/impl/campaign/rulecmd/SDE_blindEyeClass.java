package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class SDE_blindEyeClass extends BaseCommandPlugin {

    public static final String PLAYER_TRANSPONDER_SETTING = "$userTransponderSetting"; //check to see if this is 0.0f to see if was modified
    public static final String SUSPICION_BLOCKED = "$marketSuspicionBlocked";
    public static final String PLAYER_TRANSPONDER = "$userTransponder";
    public static final String PREVIOUS_SUSPICION_BLOCKED = "$marketSuspicionRecentlyBlocked";

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        if (dialog.getInteractionTarget().getMarket() == null) {
            return false;
        }

        MarketAPI market = dialog.getInteractionTarget().getMarket();
        MemoryAPI markmem = market.getMemoryWithoutUpdate();
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            blockSuspicion(market, markmem); //TODO: make it possible for backstabbing
            return true;
        }
        return false;
    }

    public void blockSuspicion(MarketAPI market, MemoryAPI memory) {
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {

            MemoryAPI globalmemory = Global.getSector().getMemoryWithoutUpdate();

            if (Global.getSector().getPlayerFleet().isTransponderOn()) {
                globalmemory.set(PLAYER_TRANSPONDER, true, 0);
                Global.getSector().getPlayerFleet().setTransponderOn(false);
            }

            float transponderSetting = Global.getSettings().getFloat("transponderOffMarketAwarenessMult");
            if (transponderSetting != 0f) {
                globalmemory.set(PLAYER_TRANSPONDER_SETTING, transponderSetting, 0);
                Global.getSettings().setFloat("transponderOffMarketAwarenessMult", 0f);
            }

            memory.set(SUSPICION_BLOCKED, true, 0);

            float cached_value = memory.getInt(PREVIOUS_SUSPICION_BLOCKED);
            cached_value++;

            memory.set(PREVIOUS_SUSPICION_BLOCKED, cached_value, 90); //support for stacking penalties

        }
    }
}

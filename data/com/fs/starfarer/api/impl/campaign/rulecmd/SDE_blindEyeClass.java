package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.CampaignEngine;

import java.util.List;
import java.util.Map;

public class SDE_blindEyeClass extends BaseCommandPlugin {

    private static final String USER_TRANSPONDER_SETTING = "$userTransponderSetting"; //check to see if this is 0.0f to see if was modified
    private static final String SUSPICION_BLOCKED = "$marketSuspicionBlocked";
    private static final String USER_TRANSPONDER = "$userTransponder";

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        if (dialog.getInteractionTarget().getMarket() == null) {
            return false;
        }

        MarketAPI market = dialog.getInteractionTarget().getMarket();
        MemoryAPI markmem = market.getMemory();
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            blockSuspicion(market, markmem); //TODO: make it possible for backstabbing
            return true;
        }
        return false;
    }

    public static boolean transponderSettingModified = false;
    public static float userTransponderSetting;
    public static boolean hasSuspicionBlocked;

    public void blockSuspicion(MarketAPI market, MemoryAPI memory) {
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {

            if (Global.getSector().getPlayerFleet().isTransponderOn()) {
                memory.set(USER_TRANSPONDER, true, 0);
                Global.getSector().getPlayerFleet().setTransponderOn(false);
            }

            float transponderSetting = Global.getSettings().getFloat("transponderOffMarketAwarenessMult");
            if (transponderSetting != 0f) {
                memory.set(USER_TRANSPONDER_SETTING, transponderSetting, 0);
                Global.getSettings().setFloat("transponderOffMarketAwarenessMult", 0f);
            }
            memory.set(SUSPICION_BLOCKED, true, 0);
            SDE_ColonyInteractionListener listener = new SDE_ColonyInteractionListener(market);
        }
    }
}

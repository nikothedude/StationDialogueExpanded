package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class SDE_hasSubmarket extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if (dialog.getInteractionTarget().getMarket() == null) {
            return false;
        }

        String submarket = params.get(0).getString(memoryMap);
        MarketAPI market = dialog.getInteractionTarget().getMarket();
        return market.hasSubmarket(submarket);
    }
}
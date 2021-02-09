package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.ids.Industries;

import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.impl.campaign.ids.Industries.*;

//btw vanilla has a lot of variables for things you need, just memdump to see them
public class SDE_checkMarketStates extends BaseCommandPlugin {

    protected PersonAPI person;
    protected MarketAPI market;

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        person = dialog.getInteractionTarget().getActivePerson();
        market = dialog.getInteractionTarget().getMarket();

        String command = params.get(0).getString(memoryMap);
        if (command == null)
            return false;
        if (dialog.getInteractionTarget().getMarket() == null && dialog.getInteractionTarget().getActivePerson() == null)
            return false;

        if (command.equals("marketHasWaystation")) {
            return market.hasWaystation();
        }

        if (command.equals("marketHasShipProduction")) {
            if (market.hasIndustry(TAG_HEAVYINDUSTRY)) {
                memoryMap.get(MemKeys.MARKET).set("$SDE_marketShipQuality", market.getShipQualityFactor(), 0);
                return true;
            }
            return false;
        }

        if (command.equals("hasOrbitalStation")) {
            return (market.hasIndustry(TAG_STATION));
        }

        if (command.equals("hasBattlestation")) {
            return (market.hasIndustry(TAG_BATTLESTATION));
        }

        if (command.equals("hasStarfortress")) {
            return (market.hasIndustry(TAG_STARFORTRESS));
        }

        return false;


    }
}

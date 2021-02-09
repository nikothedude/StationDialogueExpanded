package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class SDE_getFactionRelation extends BaseCommandPlugin {

    protected PersonAPI person;
    protected MarketAPI market;

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String command = params.get(0).getString(memoryMap);
        if (command == null)
            return false;
        if (dialog.getInteractionTarget().getMarket() == null && dialog.getInteractionTarget().getActivePerson() == null)
            return false;

        person = dialog.getInteractionTarget().getActivePerson();
        market = dialog.getInteractionTarget().getMarket();
        String getMarketFactionID = market.getFaction().getId();
        String getPersonFactionID = person.getFaction().getId();
        float getPersonFactionRelToPlayer = person.getFaction().getRelToPlayer().getRel();
        float getMarketFactionRelToPlayer = market.getFaction().getRelToPlayer().getRel();

        if (command.equals("getMarketFactionRep")) {
            memoryMap.get(MemKeys.MARKET).set("$SDE_marketFactionRep", getMarketFactionRelToPlayer, 0);
            return true;
        }

        if (command.equals("getPersonFactionRep")) {
            memoryMap.get(MemKeys.PERSON_FACTION).set("$SDE_personFactionRep", getPersonFactionRelToPlayer, 0);
            return true;
        }



        return false;
    }
}

package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.characters.SDE_personVariables.personVoiceMult;

public class SDE_calculateBribe extends BaseCommandPlugin{ //todo: use different calculation for non-bribe

    private static final String BRIBE_VALUE_CREDITS = "$bribeValueCredits";

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if (dialog.getInteractionTarget().getMarket() == null || dialog.getInteractionTarget().getActivePerson() == null) {
            return false;
        }

        MarketAPI market = dialog.getInteractionTarget().getMarket();
        PersonAPI person = dialog.getInteractionTarget().getActivePerson();

        MemoryAPI memory = memoryMap.get(MemKeys.LOCAL);
        memory.set("$personBribeValueCredits", calculateBribe(market, person), 0);

        return true;
    }
    //TODO: break up method into multiple components, calculatePersonalityComponent, calculateFactionComponent, etc
    public static int calculateBribe(MarketAPI market, PersonAPI person){
        int bribeBase = 1000;
        int bribeValue;
        float personalityBribeMult = personVoiceMult(person);
        float marketStabilityMult = 1*(market.getStabilityValue());
        int marketSizeMult = 2*(market.getSize());
        bribeValue = (int) (bribeBase * (marketStabilityMult + marketSizeMult + personalityBribeMult));
        return bribeValue;
    }
}

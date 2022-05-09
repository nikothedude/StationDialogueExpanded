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

public class SDE_calculateBlindEyeCost extends BaseCommandPlugin{

    private static final String BLIND_EYE_VALUE_CREDITS = "$blindEyeValueCredits";

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if (dialog.getInteractionTarget().getMarket() == null || dialog.getInteractionTarget().getActivePerson() == null) {
            return false;
        }

        MarketAPI market = dialog.getInteractionTarget().getMarket();
        PersonAPI person = dialog.getInteractionTarget().getActivePerson();

        MemoryAPI memory = memoryMap.get(MemKeys.LOCAL);
        memory.set(BLIND_EYE_VALUE_CREDITS, calculateBlindEyeCostCredits(market, person), 0);

        return true;
    }

    public static int calculateBlindEyeCostCredits(MarketAPI market, PersonAPI person) { //TODO: consider requesting some cargo or smthn

        MemoryAPI marketmem = market.getMemoryWithoutUpdate();

        int bribeBase = 500;
        int bribeValue;

        float personalityBribeMult = personVoiceMult(person);
        float marketStabilityMult = 1*(market.getStabilityValue());
        int marketSizeMult = 2*(market.getSize());
        float marketPreviousBlockMult;
        float marketPreviousBlockFloat = marketmem.getFloat("$marketSuspicionRecentlyBlocked");
        if (marketPreviousBlockFloat == 0) {
            marketPreviousBlockMult = 1;
        }
        else {
            marketPreviousBlockMult = ( marketPreviousBlockFloat + 0.2f );
        }

        bribeValue = (int) ((bribeBase * (marketStabilityMult + marketSizeMult + personalityBribeMult)) * marketPreviousBlockMult);

        return bribeValue;

    }

}

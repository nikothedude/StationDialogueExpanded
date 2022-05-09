package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class SDE_calculateBribe extends BaseCommandPlugin{

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
        float personalityBribeMult;
        float marketStabilityMult = 1*(market.getStabilityValue());
        int marketSizeMult = 2*(market.getSize());
        switch(person.getVoice()) {
            case "soldier": //TODO: arbitrary values for now
                personalityBribeMult = 1.5f;
                break;
            case "spacer":
                personalityBribeMult = 1.0f;
                break;
            case "faithful":
                personalityBribeMult = 2.2f;
                break;
            case "pather":
                personalityBribeMult = 0.8f;
                break;
            case "business":
                personalityBribeMult = 1.1f;
                break;
            case "official":
                personalityBribeMult = 3f;
                break;
            case "scientist":
                personalityBribeMult = 1.4f;
                break;
            case "villain":
                personalityBribeMult = 0.7f;
                break;
            case "aristo":
                personalityBribeMult = 1.5f;
                break;
            default:
                personalityBribeMult = 1.0f;
                break;
        }
        bribeValue = (int) (bribeBase * (marketStabilityMult + marketSizeMult + personalityBribeMult));
        return bribeValue;
    }
}

package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class SDE_repDecreaseDebug extends BaseCommandPlugin {

    protected PersonAPI person;



    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        person = dialog.getInteractionTarget().getActivePerson();
        String arg = params.get(0).getString(memoryMap);

        CoreReputationPlugin.RepActionEnvelope envelope = new CoreReputationPlugin.RepActionEnvelope(CoreReputationPlugin.RepActions.CUSTOMS_CAUGHT_SMUGGLING, 5f, dialog.getTextPanel());
        CoreReputationPlugin.RepActionEnvelope envelope2 = new CoreReputationPlugin.RepActionEnvelope(CoreReputationPlugin.RepActions.SYSTEM_BOUNTY_REWARD, 5f, dialog.getTextPanel());


        switch (arg) {

            case ("lower"):
                Global.getSector().adjustPlayerReputation(envelope, person);
                break;
            case ("raise"):
                Global.getSector().adjustPlayerReputation(envelope2, person);

        }

        return false;
    }}

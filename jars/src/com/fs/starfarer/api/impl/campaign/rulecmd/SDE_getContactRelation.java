package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.RelationshipAPI;
import com.fs.starfarer.api.util.Misc;

import javax.management.relation.Relation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.characters.RelationshipAPI.RelationshipTargetType.PERSON;
import static com.fs.starfarer.api.characters.RelationshipAPI.RelationshipTargetType.FACTION;
import static com.fs.starfarer.api.characters.RelationshipAPI.RelationshipTargetType.PLAYER;

public class SDE_getContactRelation extends BaseCommandPlugin {

    protected InteractionDialogAPI dialog;
    protected Map<String, MemoryAPI> memoryMap;
    protected PersonAPI person;

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if (dialog == null) {
            return false;
        }
        String post = memoryMap.get(MemKeys.LOCAL).getString("$postId");
        if (post == null) return false;



        this.dialog = dialog;
        this.memoryMap = memoryMap;
        person = dialog.getInteractionTarget().getActivePerson();
        float playerRel = person.getRelToPlayer().getRel();

        RepLevel playerRepLevel = RepLevel.getLevelFor(playerRel);

        String arg = params.get(0).getString(memoryMap);

        if ("neutral".equals(arg))
            return playerRepLevel==RepLevel.NEUTRAL;

        if ("favorable".equals(arg))
            return playerRepLevel==RepLevel.FAVORABLE;

        if ("welcoming".equals(arg))
            return playerRepLevel==RepLevel.WELCOMING;

        if ("friendly".equals(arg))
            return playerRepLevel==RepLevel.FRIENDLY;

        if ("cooperative".equals(arg))
            return playerRepLevel==RepLevel.COOPERATIVE;

        if ("suspicious".equals(arg))
            return playerRepLevel==RepLevel.SUSPICIOUS;

        if ("inhospitable".equals(arg))
            return playerRepLevel==RepLevel.INHOSPITABLE;

        if ("hostile".equals(arg))
            return playerRepLevel==RepLevel.HOSTILE;

        if ("vengeful".equals(arg))
            return playerRepLevel==RepLevel.VENGEFUL;


        return false;
    }
}

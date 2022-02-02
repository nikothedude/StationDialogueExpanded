package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;

import java.util.*;

public class SDE_isVanillaFaction extends BaseCommandPlugin {

    public static final Set<String> VANILLA_FACTIONS = new HashSet<>();

    static {
        VANILLA_FACTIONS.add(Factions.PLAYER);
        VANILLA_FACTIONS.add(Factions.PIRATES);
        VANILLA_FACTIONS.add(Factions.NEUTRAL);
        VANILLA_FACTIONS.add(Factions.HEGEMONY);
        VANILLA_FACTIONS.add(Factions.PERSEAN);
        VANILLA_FACTIONS.add(Factions.TRITACHYON);
        VANILLA_FACTIONS.add(Factions.DIKTAT);
        VANILLA_FACTIONS.add(Factions.INDEPENDENT);
        VANILLA_FACTIONS.add(Factions.SCAVENGERS);
        VANILLA_FACTIONS.add(Factions.REMNANTS);
        VANILLA_FACTIONS.add(Factions.LIONS_GUARD);
        VANILLA_FACTIONS.add(Factions.KOL);
        VANILLA_FACTIONS.add(Factions.MERCENARY);
        VANILLA_FACTIONS.add(Factions.OMEGA);
        VANILLA_FACTIONS.add(Factions.LUDDIC_CHURCH);
        VANILLA_FACTIONS.add(Factions.LUDDIC_PATH);
        VANILLA_FACTIONS.add(Factions.DERELICT);
        VANILLA_FACTIONS.add(Factions.SLEEPER);
        VANILLA_FACTIONS.add(Factions.POOR);
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap)
    {
        String faction = memoryMap.get(MemKeys.LOCAL).getString("$personFaction.id");
        if (faction == null) return false;

        String arg = params.get(0).getString(memoryMap);
        if (faction.equals(arg.toLowerCase(Locale.ROOT))) {
            return VANILLA_FACTIONS.contains(faction);
        }
        else return false;
    }
}

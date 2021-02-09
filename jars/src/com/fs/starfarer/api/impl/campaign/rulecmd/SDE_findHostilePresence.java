package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.intel.bases.*;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.intel.bases.LuddicPathCellsIntel;
import com.fs.starfarer.api.impl.campaign.intel.bases.LuddicPathBaseIntel;



import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.impl.campaign.ids.Conditions.PATHER_CELLS;
import static com.fs.starfarer.api.impl.campaign.ids.Conditions.PIRATE_ACTIVITY;
import static com.fs.starfarer.api.impl.campaign.intel.bases.PirateBaseIntel.PirateBaseTier;
import static com.fs.starfarer.api.impl.campaign.intel.bases.PirateBaseIntel.PirateBaseTier.*;


public class SDE_findHostilePresence extends BaseCommandPlugin {

    protected StarSystemAPI system;
    protected MarketAPI market;
    protected PersonAPI person;
    protected MemoryAPI memory;
    protected SectorEntityToken target;
    protected PirateBaseIntel source;
    protected PirateBaseTier tier;
    protected String factionId;
    protected LuddicPathCellsIntel intel;



    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String command = params.get(0).getString(memoryMap);

        market = dialog.getInteractionTarget().getMarket();
        person = dialog.getInteractionTarget().getActivePerson();
        boolean isLuddicCellPresent = dialog.getInteractionTarget().getMarket().hasCondition(PATHER_CELLS);
        boolean doesSystemHavePirateActivity = dialog.getInteractionTarget().getMarket().hasCondition(PIRATE_ACTIVITY);

        PirateBaseIntel originTierPirate = new PirateBaseIntel(system, factionId, tier);
        PirateBaseTier getPirateBaseTier = originTierPirate.getTier();


        if (!doesSystemHavePirateActivity && !isLuddicCellPresent)
            return false;

        if (command.equals("hasPatherCells")) { //btw vanilla already has a variable for this
            return isLuddicCellPresent;
        }

        if (command.equals("hasPirateActivity")) {
            return doesSystemHavePirateActivity;
        }

        switch (command) {

            case "isBaseTierOne":
                return (getPirateBaseTier.equals(TIER_1_1MODULE));

            case "isBaseTierTwo":
                return (getPirateBaseTier.equals(TIER_2_1MODULE));

            case "isBaseTierThree":
                return (getPirateBaseTier.equals(TIER_3_2MODULE));

            case "isBaseTierFour":
                return (getPirateBaseTier.equals(TIER_4_3MODULE));

            case "isBaseTierFive":
                return (getPirateBaseTier.equals(TIER_5_3MODULE));

            default:
                return false;

        }


    }
}

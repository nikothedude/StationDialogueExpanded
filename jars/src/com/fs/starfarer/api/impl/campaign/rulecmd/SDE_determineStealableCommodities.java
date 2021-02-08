package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketDemandAPI;
import com.fs.starfarer.api.campaign.econ.MarketDemandDataAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.impl.campaign.ids.Commodities.SUPPLIES;

public class SDE_determineStealableCommodities extends BaseCommandPlugin {

    protected PersonAPI person;
    protected MemoryAPI memory;
    protected SectorEntityToken target;

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        person = dialog.getInteractionTarget().getActivePerson();


        float marketStab = dialog.getInteractionTarget().getMarket().getStabilityValue();
    //    target = dialog.getInteractionTarget();
   //     MarketAPI market = target.getMarket();
        //       MarketDemandDataAPI demandData = Global.getSector().getEconomy().getMarket(String.valueOf(market)).getDemandData();
        int demandData = dialog.getInteractionTarget().getMarket().getCommodityData(SUPPLIES).getDeficitQuantity();
       // float demandData = (float) demandData1;
        float demandDataValue = dialog.getInteractionTarget().getMarket().getDemand(SUPPLIES).getDemandValue();

        memory = getEntityMemory(memoryMap);

        String command = params.get(0).getString(memoryMap);
            if (person == null)
                return false;
            if (command == null)
                return false;
            if (dialog.getInteractionTarget().getMarket() == null)
                return false;

        if (command.equals("marketExploitableByInsider")) {
            if (marketStab <= 5) //placeholder value for debugging {
                memory.set("$SDE_demandData", demandData, 0);
                memory.set("$SDE_demandDataValue", demandDataValue, 0);
                return true;
            }
            else return false;

    }
}

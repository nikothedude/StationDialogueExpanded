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
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.impl.campaign.ids.Commodities.SUPPLIES;

public class SDE_determineStealableCommodities extends BaseCommandPlugin {

    protected PersonAPI person;
    protected MemoryAPI memory;
    protected SectorEntityToken target;

    public static Logger log = Global.getLogger(SDE_determineStealableCommodities.class);


    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        person = dialog.getInteractionTarget().getActivePerson();


        float marketStab = dialog.getInteractionTarget().getMarket().getStabilityValue();
        int demandDeficit = dialog.getInteractionTarget().getMarket().getCommodityData(SUPPLIES).getDeficitQuantity();
        int commoditySurplus = dialog.getInteractionTarget().getMarket().getCommodityData(SUPPLIES).getExcessQuantity();
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
            if (marketStab >= 5) {//placeholder value for debugging {
//               log.info("deficit is " + demandDeficit); //this doesnt even log it. it just makes the code WORK.
                memoryMap.get(MemKeys.MARKET).set("$SDE_demandDeficit", demandDeficit, 0);
                memoryMap.get(MemKeys.MARKET).set("$SDE_commoditySurplus", commoditySurplus, 0);
                memoryMap.get(MemKeys.MARKET).set("$SDE_demandDataValue", demandDataValue, 0);
            }
            return true;
            }
            else return false;
    }
}

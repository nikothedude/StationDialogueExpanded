package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.shared.PlayerTradeDataForSubmarket;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.CampaignEngine;

import java.util.List;
import java.util.Map;

public class SDE_blindEyeClass extends BaseCommandPlugin {

    private static final String USER_TRANSPONDER_SETTING = "$userTransponderSetting"; //check to see if this is 0.0f to see if was modified
    private static final String SUSPICION_BLOCKED = "$marketSuspicionBlocked";
    private static final String USER_TRANSPONDER = "$userTransponder";

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        if (dialog.getInteractionTarget().getMarket() == null) {
            return false;
        }

        MarketAPI market = dialog.getInteractionTarget().getMarket();
        MemoryAPI markmem = market.getMemory();
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            blockSuspicion(market, markmem); //TODO: make it possible for backstabbing
            return true;
        }
        return false;
    }

    public void blockSuspicion(MarketAPI market, MemoryAPI memory) {
        if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
            memory.set(SUSPICION_BLOCKED, true, 0);
            Global.getSector().addTransientListener(new CampaignEventListener() {
                @Override
                public void reportPlayerOpenedMarket(MarketAPI market) {

                }

                @Override
                public void reportPlayerClosedMarket(MarketAPI market) {

                }

                @Override
                public void reportPlayerOpenedMarketAndCargoUpdated(MarketAPI market) {

                }

                @Override
                public void reportEncounterLootGenerated(FleetEncounterContextPlugin plugin, CargoAPI loot) {

                }

                @Override
                public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {
                    MarketAPI market = transaction.getMarket();
                    MemoryAPI memory = market.getMemoryWithoutUpdate();

                    if (market.hasSubmarket(Submarkets.SUBMARKET_BLACK)) {
                        SubmarketAPI blackmarket = market.getSubmarket(Submarkets.SUBMARKET_BLACK);
                        if (memory.getBoolean("$marketSuspicionBlocked")) {
                            if (transaction.getSubmarket().getSpecId().equals("black_market")) {
                                PlayerTradeDataForSubmarket blackmarket_data = new PlayerTradeDataForSubmarket(blackmarket);
                                blackmarket_data.setTotalPlayerTradeValue(0f); //TODO: dont make this wipe suspicion, save the old suspicion
                            }
                        }
                    }
                }

                @Override
                public void reportBattleOccurred(CampaignFleetAPI primaryWinner, BattleAPI battle) {

                }

                @Override
                public void reportBattleFinished(CampaignFleetAPI primaryWinner, BattleAPI battle) {

                }

                @Override
                public void reportPlayerEngagement(EngagementResultAPI result) {

                }

                @Override
                public void reportFleetDespawned(CampaignFleetAPI fleet, FleetDespawnReason reason, Object param) {

                }

                @Override
                public void reportFleetSpawned(CampaignFleetAPI fleet) {

                }

                @Override
                public void reportFleetReachedEntity(CampaignFleetAPI fleet, SectorEntityToken entity) {

                }

                @Override
                public void reportFleetJumped(CampaignFleetAPI fleet, SectorEntityToken from, JumpPointAPI.JumpDestination to) {

                }

                @Override
                public void reportShownInteractionDialog(InteractionDialogAPI dialog) {

                }

                @Override
                public void reportPlayerReputationChange(String faction, float delta) {

                }

                @Override
                public void reportPlayerReputationChange(PersonAPI person, float delta) {

                }

                @Override
                public void reportPlayerActivatedAbility(AbilityPlugin ability, Object param) {

                }

                @Override
                public void reportPlayerDeactivatedAbility(AbilityPlugin ability, Object param) {

                }

                @Override
                public void reportPlayerDumpedCargo(CargoAPI cargo) {

                }

                @Override
                public void reportPlayerDidNotTakeCargo(CargoAPI cargo) {

                }

                @Override
                public void reportEconomyTick(int iterIndex) {

                }

                @Override
                public void reportEconomyMonthEnd() {

                }
            });
        }
    }
}

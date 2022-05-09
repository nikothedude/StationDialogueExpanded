package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.impl.campaign.listeners.SDE_ColonyInteractionListener;

public class SDE_modPlugin extends BaseModPlugin {

    @Override
    public void onGameLoad(boolean newGame) {
        SectorAPI sector = Global.getSector();
        sector.addTransientListener(new SDE_ColonyInteractionListener(false));
    }

}

package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.util.Misc;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SDE_isColonyContact extends BaseCommandPlugin {

    public static final Set<String> COMMAND_POSTS = new HashSet<>();
    public static final Set<String> MILITARY_POSTS = new HashSet<>();
    public static final Set<String> TRADER_POSTS = new HashSet<>();
    public static final Set<String> ADMIN_POSTS = new HashSet<>();
    public static final Set<String> SUPPLY_OFFICERS = new HashSet<>();


    static {
        SUPPLY_OFFICERS.add(Ranks.POST_PORTMASTER);
        SUPPLY_OFFICERS.add(Ranks.POST_SUPPLY_MANAGER);
        SUPPLY_OFFICERS.add(Ranks.POST_SUPPLY_OFFICER);
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap)
    {
        String post = memoryMap.get(MemKeys.LOCAL).getString("$postId");
        if (post == null) return false;

        String arg = params.get(0).getString(memoryMap);
        if ("trade".equals(arg.toLowerCase(Locale.ROOT))) {
            return SUPPLY_OFFICERS.contains(post);
        }
        else return false;
    }
}
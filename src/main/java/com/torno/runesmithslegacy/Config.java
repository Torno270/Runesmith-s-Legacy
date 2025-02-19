package com.torno.runesmithslegacy;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = RunesmithsLegacy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue LEVEL_XP = BUILDER
        .comment("Defines the amount of experience per level.\n50 approximates the average Xp required per level " +
            "between levels 1 and 31 in the base game.")
        .defineInRange("levelXp", 50, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue DEATH_XP_LOSS = BUILDER
        .comment("Defines the percentage of experience lost upon death.\nSet a value between 0 (no XP lost) and " +
            "100 (all XP lost).")
        .defineInRange("deathXpLoss", 50, 0, 100);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int levelXp;
    public static int deathXpLoss;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        levelXp = LEVEL_XP.get();
        deathXpLoss = DEATH_XP_LOSS.get();
    }
}

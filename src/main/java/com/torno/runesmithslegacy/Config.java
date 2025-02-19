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
        .defineInRange("deathXpLoss", 75, 0, 100);

    private static final ModConfigSpec.BooleanValue KEEP_VANILLA_LEVEL_XP = BUILDER
        .comment("Determines whether to keep the vanilla Xp per level.")
        .define("keepVanillaLevelXp", false);

    private static final ModConfigSpec.BooleanValue KEEP_VANILLA_DEATH_XP = BUILDER
        .comment("Determines whether to keep the vanilla death Xp.")
        .define("keepVanillaDeathXp", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean keepVanillaLevelXp;
    public static boolean keepVanillaDeathXp;
    public static int levelXp;
    public static int deathXpLoss;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        keepVanillaLevelXp = KEEP_VANILLA_LEVEL_XP.get();
        keepVanillaDeathXp = KEEP_VANILLA_DEATH_XP.get();
        levelXp = LEVEL_XP.get();
        deathXpLoss = DEATH_XP_LOSS.get();
    }
}

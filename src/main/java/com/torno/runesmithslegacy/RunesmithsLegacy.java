package com.torno.runesmithslegacy;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.Set;

@Mod(RunesmithsLegacy.MODID)
public class RunesmithsLegacy {
    public static final String MODID = "runesmithslegacy";

    public RunesmithsLegacy(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public static boolean hasEnchant(ItemStack stack, ResourceKey<Enchantment> enchant) {
        if (!stack.isEnchanted()) return false;
        boolean hasEnchantment = false;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            if (enchantmentHolder.is(enchant)) {
                hasEnchantment = true;
            }
        }
        return hasEnchantment;
    }
}

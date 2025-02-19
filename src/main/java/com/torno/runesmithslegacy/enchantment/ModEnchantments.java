package com.torno.runesmithslegacy.enchantment;

import com.torno.runesmithslegacy.RunesmithsLegacy;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> INSTINCTIVE_RELEASE = ResourceKey.create(Registries.ENCHANTMENT,
        ResourceLocation.fromNamespaceAndPath(RunesmithsLegacy.MODID, "instinctive_release"));
    public static final ResourceKey<Enchantment> VERSATILITY = ResourceKey.create(Registries.ENCHANTMENT,
        ResourceLocation.fromNamespaceAndPath(RunesmithsLegacy.MODID, "versatility"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var items = context.lookup(Registries.ITEM);

        register(context, INSTINCTIVE_RELEASE, Enchantment.enchantment(Enchantment.definition(
            items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
            1,
            1,
            Enchantment.constantCost(20),
            Enchantment.constantCost(50),
            8,
            EquipmentSlotGroup.MAINHAND))
        );
        register(context, VERSATILITY, Enchantment.enchantment(Enchantment.definition(
            items.getOrThrow(ItemTags.PICKAXES),
            2,
            1,
            Enchantment.dynamicCost(25, 25),
            Enchantment.dynamicCost(75, 25),
            4,
            EquipmentSlotGroup.MAINHAND))
        );
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
        Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}

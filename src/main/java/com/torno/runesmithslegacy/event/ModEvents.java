package com.torno.runesmithslegacy.event;

import com.torno.runesmithslegacy.RunesmithsLegacy;
import com.torno.runesmithslegacy.enchantment.ModEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.Set;

import static com.torno.runesmithslegacy.RunesmithsLegacy.hasEnchant;
import static net.minecraft.world.item.Tiers.*;

@EventBusSubscriber(modid = RunesmithsLegacy.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void instinctiveRelease(LivingEntityUseItemEvent.Tick event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        ItemStack stack = event.getItem();
        if (!(stack.getItem() instanceof BowItem)) return;
        if (!stack.isEnchanted()) return;

        int useDuration = stack.getUseDuration(entity);
        int useItemRemainingTicks = entity.getUseItemRemainingTicks();
        int useTime = useDuration - useItemRemainingTicks;

        if (useTime >= 20) {
            boolean hasEnchantment = false;
            Set<Object2IntMap.Entry<Holder<Enchantment>>> enchantmentSet = stack.getTagEnchantments().entrySet();
            for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantmentSet) {
                Holder<Enchantment> enchantmentHolder = entry.getKey();

                if (enchantmentHolder.is(ModEnchantments.INSTINCTIVE_RELEASE)) {
                    hasEnchantment = true;
                }
            }
            if (!hasEnchantment) return;
            //entity.sendSystemMessage(Component.literal("Instinctive Release"));
            entity.releaseUsingItem();
        }
    }

    @SubscribeEvent
    public static void versatilitySpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof PickaxeItem)) return;
        if (!stack.isEnchanted()) return;
        if (!hasEnchant(stack, ModEnchantments.VERSATILITY)) return;

        Tier tier;
        if (stack.getItem() instanceof TieredItem tieredItem) {
            tier = tieredItem.getTier();
        } else {
            tier = NETHERITE;
        }

        BlockState state = event.getState();
        Item equivalentTool = getFastestTool(stack.getDestroySpeed(state), state, tier);
        if (equivalentTool == null) return;

        ItemStack equivalentToolStack = new ItemStack(equivalentTool);
        float equivalentSpeed = equivalentToolStack.getDestroySpeed(state);
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();

            if (enchantmentHolder.is(Enchantments.EFFICIENCY)) {
                int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(enchantmentHolder, player);
                int add = (int) (Math.pow(efficiencyLevel, 2) + 1);
                equivalentSpeed = equivalentSpeed + add;
            }
        }

        event.setNewSpeed(equivalentSpeed);
    }

    private static Item getFastestTool(float baseSpeed, BlockState state, Tier tier) {
        Item fastestTool = null;
        ArrayList<Item> tools = new ArrayList<>();
        tools.add(Items.SHEARS);
        tools.add(getSwordFromTier(tier));
        tools.add(getAxeFromTier(tier));
        tools.add(getHoeFromTier(tier));
        tools.add(getShovelFromTier(tier));
        float speed = baseSpeed;
        for (Item tool : tools) {
            float toolSpeed = new ItemStack(tool).getDestroySpeed(state);
            if (toolSpeed > speed) {
                speed = toolSpeed;
                fastestTool = tool;
            }
        }
        return fastestTool;
    }

    private static Item getSwordFromTier(Tier tier) {
        return switch (tier) {
            case WOOD -> Items.WOODEN_SWORD;
            case STONE -> Items.STONE_SWORD;
            case IRON -> Items.IRON_SWORD;
            case GOLD -> Items.GOLDEN_SWORD;
            case DIAMOND -> Items.DIAMOND_SWORD;
            default -> Items.NETHERITE_SWORD;
        };
    }

    private static Item getAxeFromTier(Tier tier) {
        return switch (tier) {
            case WOOD -> Items.WOODEN_AXE;
            case STONE -> Items.STONE_AXE;
            case IRON -> Items.IRON_AXE;
            case GOLD -> Items.GOLDEN_AXE;
            case DIAMOND -> Items.DIAMOND_AXE;
            default -> Items.NETHERITE_AXE;
        };
    }

    private static Item getHoeFromTier(Tier tier) {
        return switch (tier) {
            case WOOD -> Items.WOODEN_HOE;
            case STONE -> Items.STONE_HOE;
            case IRON -> Items.IRON_HOE;
            case GOLD -> Items.GOLDEN_HOE;
            case DIAMOND -> Items.DIAMOND_HOE;
            default -> Items.NETHERITE_HOE;
        };
    }

    private static Item getShovelFromTier(Tier tier) {
        return switch (tier) {
            case WOOD -> Items.WOODEN_SHOVEL;
            case STONE -> Items.STONE_SHOVEL;
            case IRON -> Items.IRON_SHOVEL;
            case GOLD -> Items.GOLDEN_SHOVEL;
            case DIAMOND -> Items.DIAMOND_SHOVEL;
            default -> Items.NETHERITE_SHOVEL;
        };
    }
}

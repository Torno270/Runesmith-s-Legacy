package com.torno.runesmithslegacy.event;

import com.torno.runesmithslegacy.RunesmithsLegacy;
import com.torno.runesmithslegacy.enchantment.ModEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static com.torno.runesmithslegacy.RunesmithsLegacy.hasEnchant;

@EventBusSubscriber(modid = RunesmithsLegacy.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void instinctiveRelease(LivingEntityUseItemEvent.Tick event) {
        LivingEntity entity = event.getEntity();
        ItemStack stack = event.getItem();

        if (!(entity instanceof Player)) return;
        if (!(stack.getItem() instanceof BowItem)) return;
        if (!stack.isEnchanted()) return;

        int useDuration = stack.getUseDuration(entity);
        int useItemRemainingTicks = entity.getUseItemRemainingTicks();
        int useTime = useDuration - useItemRemainingTicks;

        if (useTime >= 20) {
            if (!hasEnchant(stack, ModEnchantments.INSTINCTIVE_RELEASE)) return;
            //entity.sendSystemMessage(Component.literal("Instinctive Release"));
            entity.releaseUsingItem();
        }
    }

    @SubscribeEvent
    public static void versatilitySpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        BlockState state = event.getState();

        if (!(stack.getItem() instanceof PickaxeItem)) return;
        if (!hasEnchant(stack, ModEnchantments.VERSATILITY)) return;

        float properOnBlock = getProperOnBlock(state);
        float baseOnStone = new ItemStack(Items.NETHERITE_PICKAXE).getDestroySpeed(Blocks.STONE.defaultBlockState());
        float currentOnStone = stack.getDestroySpeed(Blocks.STONE.defaultBlockState());

        float newSpeed = properOnBlock / baseOnStone * currentOnStone +
            (float) (Math.pow(getEnchantLevel(stack, Enchantments.EFFICIENCY, player), 2) + 1);

        //player.sendSystemMessage(Component.literal("newSpeed: " + newSpeed));
        event.setNewSpeed(newSpeed);
    }

    private static float getProperOnBlock(BlockState state) {
        Item[] tools = {
            Items.NETHERITE_SWORD,
            Items.NETHERITE_SHOVEL,
            Items.NETHERITE_PICKAXE,
            Items.NETHERITE_AXE,
            Items.NETHERITE_HOE,
            Items.SHEARS
        };
        float maxSpeed = 0;
        for (Item tool : tools) {
            float toolSpeed = new ItemStack(tool).getDestroySpeed(state);
            maxSpeed = Math.max(maxSpeed, toolSpeed);
        }
        return maxSpeed;
    }

    private static int getEnchantLevel(ItemStack stack, ResourceKey<Enchantment> enchant, LivingEntity entity) {
        if (!stack.isEnchanted()) return 0;
        int enchantmentLevel = 0;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            if (enchantmentHolder.is(enchant)) {
                enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(enchantmentHolder, entity);
            }
        }
        return enchantmentLevel;
    }
}

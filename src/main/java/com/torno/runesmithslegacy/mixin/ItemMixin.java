package com.torno.runesmithslegacy.mixin;

import com.torno.runesmithslegacy.enchantment.ModEnchantments;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.torno.runesmithslegacy.RunesmithsLegacy.hasEnchant;
import static net.minecraft.world.item.Tiers.*;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void versatilityLoot(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof PickaxeItem && hasEnchant(stack, ModEnchantments.VERSATILITY)) {
            if (stack.getItem() instanceof TieredItem tieredItem) {
                if (getMiningLevel(tieredItem.getTier()) >= getRequiredMiningLevel(state)) {
                    cir.setReturnValue(true);
                }
            } else {
                cir.setReturnValue(true);
            }
        }
    }

    private int getMiningLevel(Tier tier) {
        return switch (tier) {
            case WOOD, GOLD -> 0;
            case STONE -> 1;
            case IRON -> 2;
            case DIAMOND -> 3;
            default -> 4;
        };
    }

    private int getRequiredMiningLevel(BlockState state) {
        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) return 3;
        if (state.is(BlockTags.NEEDS_IRON_TOOL)) return 2;
        if (state.is(BlockTags.NEEDS_STONE_TOOL)) return 1;
        return 0;
    }
}

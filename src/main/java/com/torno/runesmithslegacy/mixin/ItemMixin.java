package com.torno.runesmithslegacy.mixin;

import com.torno.runesmithslegacy.enchantment.ModEnchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.torno.runesmithslegacy.RunesmithsLegacy.hasEnchant;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void versatilityLoot(ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof PickaxeItem && stack.isEnchanted() && hasEnchant(stack, ModEnchantments.VERSATILITY)) {
            cir.setReturnValue(true);
        }
    }
}

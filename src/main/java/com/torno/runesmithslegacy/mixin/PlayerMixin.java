package com.torno.runesmithslegacy.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerAccessor {
    @ModifyReturnValue(method = "getXpNeededForNextLevel", at = @At("RETURN"))
    private int linearLevelXp(int original) {
        return 46;
    }

    @ModifyReturnValue(method = "getBaseExperienceReward", at = @At("RETURN"))
    private int deathXp(int original) {
        if (original == 0) {
            return original;
        } else {
            return Math.ceilDiv(getTotalExperience(), 2);
        }
    }
}

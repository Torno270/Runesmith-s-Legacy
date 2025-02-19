package com.torno.runesmithslegacy.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.torno.runesmithslegacy.Config;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerAccessor {
    @ModifyReturnValue(method = "getXpNeededForNextLevel", at = @At("RETURN"))
    private int linearLevelXp(int original) {
        if (Config.keepVanillaLevelXp) {
            return original;
        } else {
            return Config.levelXp;
        }
    }

    @ModifyReturnValue(method = "getBaseExperienceReward", at = @At("RETURN"))
    private int deathXp(int original) {
        if (Config.keepVanillaDeathXp || original == 0) {
            return original;
        } else {
            return Math.round((getExperienceLevel() * Config.levelXp + getExperienceProgress() * Config.levelXp) *
                (1 - Config.deathXpLoss / 100.0f));
        }
    }
}

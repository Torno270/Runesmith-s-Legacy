package com.torno.runesmithslegacy.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Player.class)
public interface PlayerAccessor {
    @Accessor("experienceLevel")
    int getExperienceLevel();

    @Accessor("experienceProgress")
    float getExperienceProgress();
}

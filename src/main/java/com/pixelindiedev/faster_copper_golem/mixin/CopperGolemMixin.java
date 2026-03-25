package com.pixelindiedev.faster_copper_golem.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pixelindiedev.faster_copper_golem.config.ClientConfigCache;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CopperGolem.class, priority = 800)
public abstract class CopperGolemMixin {
    @Unique
    private CopperGolem self;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void captureData(EntityType type, Level level, CallbackInfo ci) {
        self = (CopperGolem) (Object) this;
    }

    @Inject(method = "setupAnimationStates", at = @At("HEAD"))
    private void accelerateAnimations(CallbackInfo ci) {
        int speed = (int) (1 / ClientConfigCache.speedMultiplier);

        accelerate(self.getIdleAnimationState(), speed);
        accelerate(self.getInteractionGetItemAnimationState(), speed);
        accelerate(self.getInteractionGetNoItemAnimationState(), speed);
        accelerate(self.getInteractionDropItemAnimationState(), speed);
        accelerate(self.getInteractionDropNoItemAnimationState(), speed);
    }

    @ModifyExpressionValue(method = "setupAnimationStates", at = @At(value = "CONSTANT", args = "floatValue=10.0F"))
    private float editSpinHeadTimerPlus(float original) {
        return original * ClientConfigCache.speedMultiplier;
    }

    @ModifyExpressionValue(method = "setupAnimationStates", at = @At(value = "CONSTANT", args = "intValue=200"))
    private int editRandomNext(int original) {
        return (int) (original * ClientConfigCache.speedMultiplier);
    }

    @ModifyExpressionValue(method = "setupAnimationStates", at = @At(value = "CONSTANT", args = "intValue=240"))
    private int editRandomNext2(int original) {
        return (int) (original * ClientConfigCache.speedMultiplier);
    }

    @Unique
    private void accelerate(AnimationState state, int speed) {
        if (state.isStarted()) {
            if (speed > 4) {
                state.stop();
            } else {
                state.fastForward(1, speed - 1);
            }
        }
    }
}

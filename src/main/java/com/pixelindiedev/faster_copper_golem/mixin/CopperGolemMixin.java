package com.pixelindiedev.faster_copper_golem.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.pixelindiedev.faster_copper_golem.config.ClientConfigCache.speedMultiplier;

@Mixin(value = CopperGolem.class, priority = 800)
public abstract class CopperGolemMixin {
    @Unique
    private CopperGolem self;
    @Unique
    private float cachedDividor = 1;
    @Unique
    private float cachedDividorOrigin = -1f;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void captureData(EntityType type, Level level, CallbackInfo ci) {
        self = (CopperGolem) (Object) this;
    }

    @Inject(method = "setupAnimationStates", at = @At("TAIL"))
    private void accelerateAnimations(CallbackInfo ci) {
        float multiplier = speedMultiplier;
        if (multiplier == 1.0f) return;
        getCachedDividor(speedMultiplier);

//        accelerate(self.getIdleAnimationState());
        accelerate(self.getInteractionGetItemAnimationState());
        accelerate(self.getInteractionGetNoItemAnimationState());
        accelerate(self.getInteractionDropItemAnimationState());
        accelerate(self.getInteractionDropNoItemAnimationState());
    }

    @ModifyExpressionValue(method = "setupAnimationStates", at = @At(value = "CONSTANT", args = "floatValue=10.0F"))
    private float editSpinHeadTimerPlus(float original) {
        return original * speedMultiplier;
    }

    @ModifyExpressionValue(method = "setupAnimationStates", at = @At(value = "CONSTANT", args = "intValue=200"))
    private int editRandomNext(int original) {
        return (int) (original * speedMultiplier);
    }

    @ModifyExpressionValue(method = "setupAnimationStates", at = @At(value = "CONSTANT", args = "intValue=240"))
    private int editRandomNext2(int original) {
        return (int) (original * speedMultiplier);
    }

    @Unique
    private void accelerate(AnimationState state) {
        if (state.isStarted()) {
            //stop anim when fastest
            if (cachedDividor > 5) {
                state.stop();
            } else {
                state.fastForward(1, cachedDividor);
            }
        }
    }

    @Unique
    void getCachedDividor(float multiplier) {
        if (cachedDividorOrigin != multiplier) {
            cachedDividorOrigin = multiplier;
            cachedDividor = 1.0f / cachedDividorOrigin - 0.1f;
        }
    }
}

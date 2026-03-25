package com.pixelindiedev.faster_copper_golem.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.pixelindiedev.faster_copper_golem.Faster_copper_golem.*;

@Mixin(value = TransportItemsBetweenContainers.class, priority = 800)
public abstract class MoveItemsTaskMixin {
    @Mutable
    @Final
    @Shadow
    private int horizontalSearchDistance;
    @Mutable
    @Final
    @Shadow
    private int verticalSearchDistance;
    @Mutable
    @Final
    @Shadow
    private float speedModifier;

    @ModifyExpressionValue(method = "pickupItemFromContainer", at = @At(value = "CONSTANT", args = "intValue=16"))
    private static int increaseStackAmount(int original) {
        return getMaxStackSize();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void captureData(float speed, Predicate inputContainerPredicate, Predicate outputChestPredicate, int horizontalRange, int verticalRange, Map interactionCallbacks, Consumer travellingCallback, Predicate storagePredicate, CallbackInfo ci) {
        horizontalSearchDistance = getHorizontalSearchRadius();
        verticalSearchDistance = getVerticalSearchRadius();
        speedModifier = getMovementSpeed();

        AddTask((TransportItemsBetweenContainers) (Object) this);
    }

    @ModifyExpressionValue(method = "onReachedTarget", at = @At(value = "CONSTANT", args = "intValue=60"))
    private int reduceInteractionTime(int original) {
        return getInteractionTime(original);
    }

    @ModifyExpressionValue(method = "enterCooldownAfterNoMatchingTargetFound", at = @At(value = "CONSTANT", args = "intValue=140"))
    private int reduceCooldown(int original) {
        return getCooldownTime(original);
    }

    @ModifyExpressionValue(method = "setVisitedBlockPos", at = @At(value = "CONSTANT", args = "intValue=10"))
    private int increaseVisitedChestMemory(int original) {
        return getMaxChestsRemembered(original);
    }
}

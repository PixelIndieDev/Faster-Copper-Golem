package com.pixelindiedev.faster_copper_golem.mixin;

import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TransportItemsBetweenContainers.class)
public interface MoveItemsTaskAccessor {
    @Accessor("horizontalSearchDistance")
    void setHorizontalRange(int value);

    @Accessor("verticalSearchDistance")
    void setVerticalRange(int value);

    @Accessor("speedModifier")
    void setSpeed(float value);
}

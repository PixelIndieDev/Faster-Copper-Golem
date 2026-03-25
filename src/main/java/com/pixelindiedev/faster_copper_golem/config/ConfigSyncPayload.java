package com.pixelindiedev.faster_copper_golem.config;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ConfigSyncPayload(float speedMultiplier, float movementSpeed, int interactionTime, int cooldownTime, int maxStackSize, int maxChestsRemembered, int horizontalRange, int verticalRange) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ConfigSyncPayload> ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("faster_copper_golem", "config_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, ConfigSyncPayload::speedMultiplier,
            ByteBufCodecs.FLOAT, ConfigSyncPayload::movementSpeed,
            ByteBufCodecs.VAR_INT, ConfigSyncPayload::interactionTime,
            ByteBufCodecs.VAR_INT, ConfigSyncPayload::cooldownTime,
            ByteBufCodecs.VAR_INT, ConfigSyncPayload::maxStackSize,
            ByteBufCodecs.VAR_INT, ConfigSyncPayload::maxChestsRemembered,
            ByteBufCodecs.VAR_INT, ConfigSyncPayload::horizontalRange,
            ByteBufCodecs.VAR_INT, ConfigSyncPayload::verticalRange,
            ConfigSyncPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

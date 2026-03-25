package com.pixelindiedev.faster_copper_golem.config.integration;

import com.pixelindiedev.faster_copper_golem.config.InteractionTime;
import com.pixelindiedev.faster_copper_golem.config.RememberCountEnum;
import com.pixelindiedev.faster_copper_golem.config.SearchRadiusEnum;
import com.terraformersmc.modmenu.gui.widget.ModMenuButtonWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen extends Screen {
    private final Screen parent;
    private final com.pixelindiedev.faster_copper_golem.config.ModModConfig config;

    protected ModConfigScreen(Screen parent) {
        super(Component.literal("Faster Copper Golem Config"));
        this.parent = parent;
        this.config = com.pixelindiedev.faster_copper_golem.config.ModModConfig.load();
    }

    @Override
    protected void init() {
        int y = height / 4;

        addRenderableWidget(ModMenuButtonWidget.builder(Component.literal("Interaction time: " + config.gollemInteractionTime), (btn) ->
        {
            InteractionTime[] values = InteractionTime.values();
            int next = (config.gollemInteractionTime.ordinal() + 1) % values.length;
            config.gollemInteractionTime = values[next];
            btn.setMessage(Component.literal("Interaction time: " + config.gollemInteractionTime));
            config.save();
        }).bounds(width / 2 - 125, y, 250, 20).build());

        y += 25;

        addRenderableWidget(new AbstractSliderButton(width / 2 - 125, y, 250, 20, Component.literal("Maximum carry size: " + config.gollemMaxStackSize), (double) (config.gollemMaxStackSize - 16) / (64 - 16)) {
            @Override
            protected void updateMessage() {
                int value = 16 + (int) (this.value * (64 - 16));
                setMessage(Component.literal("Maximum Carry Size: " + value));
            }

            @Override
            protected void applyValue() {
                config.gollemMaxStackSize = 16 + (int) (this.value * (64 - 16));
                config.save();
            }
        });

        y += 25;

        addRenderableWidget(ModMenuButtonWidget.builder(Component.literal("Search radius: " + config.gollemSearchRadius), (btn) ->
        {
            SearchRadiusEnum[] values = SearchRadiusEnum.values();
            int next = (config.gollemSearchRadius.ordinal() + 1) % values.length;
            config.gollemSearchRadius = values[next];
            btn.setMessage(Component.literal("Search radius: " + config.gollemSearchRadius));
            config.save();
        }).bounds(width / 2 - 125, y, 250, 20).build());

        y += 25;

        addRenderableWidget(ModMenuButtonWidget.builder(Component.literal("Movement speed: " + config.gollemMovingSpeed), (btn) ->
        {
            InteractionTime[] values = InteractionTime.values();
            int next = (config.gollemMovingSpeed.ordinal() + 1) % values.length;
            config.gollemMovingSpeed = values[next];
            btn.setMessage(Component.literal("Movement Speed: " + config.gollemMovingSpeed));
            config.save();
        }).bounds(width / 2 - 125, y, 250, 20).build());

        y += 25;

        addRenderableWidget(ModMenuButtonWidget.builder(Component.literal("Max amount of chests to check: " + config.gollemAmountChestRemembered), (btn) ->
        {
            RememberCountEnum[] values = RememberCountEnum.values();
            int next = (config.gollemAmountChestRemembered.ordinal() + 1) % values.length;
            config.gollemAmountChestRemembered = values[next];
            btn.setMessage(Component.literal("Max Amount of chests to check: " + config.gollemAmountChestRemembered));
            config.save();
        }).bounds(width / 2 - 125, y, 250, 20).build());


        y += 30;

        addRenderableWidget(ModMenuButtonWidget.builder(Component.literal("Done"), (btn) -> Minecraft.getInstance().setScreen(parent)).bounds(width / 2 - 100, y, 200, 20).build());
    }

    @Override
    public void onClose() {
        config.save();
        minecraft.setScreen(parent);
    }
}

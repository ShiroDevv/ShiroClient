//* ShiroDev May 25 2023
//* TeleportMod.java
//*  Modified by :

//! Please leave credits.

//! For some reason, even when sending tons of extra packets to keep the same distance, the server says I am moving too fast. I don't understand why, and I can't 
//! find any info on how this networking stuff works. Max distance is 10, even when I send multiple packets each saying to move 10.
//! Fabrics docs suck, half of it not even made, and there is liveoverflows videos, but he doesn't have public source code for some reason.
//! I am going to put this on hold. If you know how to fix this wierd problem

//? For some reason when doing large numbers, it only tells me I travelled too much 3 times.
//? Checked when trying to tp 100 blocks.

//! Feel free to contact me if you have an idea to fix it!
//! Contact me at puppynuff@gmail.com or thelobsterlegion@gmail.com

//! You might now want to work on this file.
//! Spaghetti code, barely works, and this is all of the comments you are getting. There is nothing past here
//! Continue at your own risk.
//! Please don't sue me if you don't make it out alive.

package net.shirodev.shiroclient.mods;

import java.util.Timer;
import java.util.TimerTask;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.shirodev.shiroclient.BaseMod;
import net.shirodev.shiroclient.Settings;
import net.shirodev.shiroclient.Utils;

public class teleportMod extends BaseMod implements ClientCommandRegistrationCallback {
    int distance = 5;
    double distanceLeft;

    public teleportMod() {
        ClientCommandRegistrationCallback.EVENT.register(this);
    }

    @Override
    public void toggle() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (Settings.player == null && client.player != null)
            Settings.player = client.player;
        if (client.player == null)
            return;

        ClientPlayerEntity p = client.player;

        Timer tpTimer = new Timer();

        distanceLeft = distance;

        // * For prod
        // ! When testing a fix, remove this.
        if (distanceLeft > 10)
            distanceLeft = 10;

        tpTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (distanceLeft <= 10) {
                    this.cancel();
                    return;
                }
                Vec3d targetPosition = p.getPos().add(p.getRotationVector().multiply(10));

                Utils.sendPacket(
                        new PlayerMoveC2SPacket.PositionAndOnGround(targetPosition.x, targetPosition.y,
                                targetPosition.z,
                                true));
                distanceLeft = distanceLeft - 10;

                p.setPosition(targetPosition);

                Utils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(p.getX(),
                        p.getY(), p.getZ(),
                        true));

                p.sendMessage(Text.of(String.format("%s distance left.", distanceLeft)), false);

            }

        }, 20, 20);

        p.sendMessage(Text.of(String.format("%s distance left.", distanceLeft)), false);

        Vec3d targetPosition = p.getPos().add(p.getRotationVector().multiply(distanceLeft));

        p.setPosition(targetPosition);

        return;
    }

    public int setDistance(CommandContext<FabricClientCommandSource> command) {
        command.getSource().getEntity()
                .sendMessage(Text.of("Got command!"));

        distance = command.getArgument("distance", Integer.class);
        return 1;
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess) {

        dispatcher
                .register(ClientCommandManager.literal("tpDistance")
                        .then(ClientCommandManager.argument("distance", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Settings.tpMod.setDistance(context);
                                    return 1;
                                }))
                        .executes(context -> {
                            context.getSource().sendError(Text.of("Missing distance"));
                            return 1;
                        }));
    }
}
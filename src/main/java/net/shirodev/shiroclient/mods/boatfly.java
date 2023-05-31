package net.shirodev.shiroclient.mods;

import java.util.Timer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.shirodev.shiroclient.BaseMod;

public class boatfly extends BaseMod implements ClientCommandRegistrationCallback {
    Timer timer;
    float Speed;

    public boatfly() {
        super();
        ClientCommandRegistrationCallback.EVENT.register(this);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!enabled || client.player == null)
                return;

            if (!client.player.hasVehicle())
                return;

            Entity vehicle = client.player.getVehicle();
            Vec3d velocity = vehicle.getVelocity();

            double motionX = velocity.x;
            double motionY = 0;
            double motionZ = velocity.z;

            if (client.options.jumpKey.isPressed()) {
                motionY += 1;
            } else if (client.options.sprintKey.isPressed()) {
                motionY = velocity.y;
            }

            if (client.options.forwardKey.isPressed()) {
                double speed = Speed;

                float yawRad = vehicle.getYaw() * MathHelper.RADIANS_PER_DEGREE;

                motionX = MathHelper.sin(-yawRad) * speed;
                motionZ = MathHelper.cos(yawRad) * speed;
            }

            vehicle.setVelocity(motionX, motionY, motionZ);
        });

    }

    public void setSpeed(float speed) {
        Speed = speed;
    }

    @Override
    public void toggle() {
        super.toggle();
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("boatFlySpeed")
                .then(ClientCommandManager.argument("flySpeed", FloatArgumentType.floatArg()).executes(context -> {
                    float flySpeed = context.getArgument("flySpeed",
                            float.class);

                    setSpeed(flySpeed);
                    return 1;
                })).executes(context -> {
                    context.getSource().sendError(Text.of("Missing flySpeed"));
                    return 1;
                }));
    }
}

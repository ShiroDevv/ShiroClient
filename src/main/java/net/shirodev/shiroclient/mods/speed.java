package net.shirodev.shiroclient.mods;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Vec3d;
import net.shirodev.shiroclient.BaseMod;

public class speed extends BaseMod {

    @Override
    public void toggle() {
        enabled = !enabled;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!enabled)
                return;
            if (client.player == null)
                return;

            if (client.player.forwardSpeed == 0 && client.player.sidewaysSpeed == 0)
                return;

            if (!client.player.isOnGround())
                return;

            if (client.player.forwardSpeed > 0 && !client.player.horizontalCollision)
                client.player.setSprinting(true);

            Vec3d velocity = client.player.getVelocity();

            client.player.setVelocity(velocity.x * 1.8, velocity.y, velocity.z * 1.8);

            velocity = client.player.getVelocity();

            double currentSpeed = Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.z, 2));

            double maxSpeed = 0.66F;

            if (currentSpeed > maxSpeed) {
                client.player.setVelocity(velocity.x / currentSpeed * maxSpeed, velocity.y,
                        velocity.z / currentSpeed * maxSpeed);
            }

        });
    }
}

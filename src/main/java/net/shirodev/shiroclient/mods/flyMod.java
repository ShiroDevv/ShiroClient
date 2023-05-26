//*  ShiroDev May 23 2023.
//*  flyHack.java
//*  Modified by : 

//!  Please leave credits.
package net.shirodev.shiroclient.mods;

//* Import modules
import java.util.Timer;
import java.util.TimerTask;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.shirodev.shiroclient.BaseMod;
import net.shirodev.shiroclient.Settings;
import net.shirodev.shiroclient.Utils;

//* Flyhack class
public class flyMod extends BaseMod implements HudRenderCallback {
    public static flyMod INSTANCE;

    static Timer fallTimer = new Timer();

    public static double count = 0d;

    // * Constructor
    public flyMod() {
        super();
        INSTANCE = this;
    }

    // * Toggle to run the mod, if enabled
    @Override
    public void toggle() {
        // * Quicker way to change it from true -> false, or false -> true
        super.toggle();

        Settings.player = MinecraftClient.getInstance().player;

        // * If the module got enabled
        if (enabled == true) {
            // * Get the abilities, and tell minecraft that you can fly now
            Settings.player.getAbilities().allowFlying = true;

            // * Create the timer so that the game doesn't disconnect us for flying.
            fallTimer = new Timer();

            // * Make the packet be sent at a fixed rate.
            fallTimer.scheduleAtFixedRate(new TimerTask() {
                // * Keep count of the last count.
                double prevCount = 0;

                // * Override the run function
                @Override
                public void run() {
                    // * If the player doesn't exist, cancel the mod
                    if (Settings.player == null) {
                        this.cancel();
                        return;
                    }

                    // * If the player isn't flying, ignore this
                    if (!Settings.player.getAbilities().flying)
                        return;

                    // * Check if we should send the packet
                    if (prevCount == flyMod.count) {
                        // * Increate the previous count
                        prevCount++;
                        // * Tell the server that we are at these coordinates
                        // ? We subtract -0.4 from the y value, since we need the server to thing we are
                        // falling.
                        Utils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Settings.player.getX(),
                                Settings.player.getY() - 0.6, Settings.player.getZ(),
                                Settings.player.isOnGround()));
                    } else {
                        // * Set the counts to be equal to eachother
                        prevCount = flyMod.count;
                    }
                }
            }, 200, 200);
        } else {
            // * remove the players ability to fly, stop them from flying
            // * Reset The count, and cancel the timer.
            Settings.player.getAbilities().allowFlying = false;
            Settings.player.getAbilities().flying = false;
            count = 0;
            fallTimer.cancel();
        }

    }

    @Override
    public String getName() {
        return "Fly";
    }

    @Override
    public String getToolTip() {
        return "Allows you to fly in survival!";
    }

    @Override
    public String getHelpMessage() {
        return """
                Fly - Fly like you are in creative.
                Can still take fall damage.
                """;
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.player.sendMessage(Text.of("Drawing!"), false);

        // if (enabled == true) {
        client.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "fly", 100, 100, 0, false);
        // }
    }
}

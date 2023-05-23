//*  ShiroDev May 23 2023.
//*  flyHack.java
//*  Modified by : 

//!  Please leave credits.
package net.shirodev.shiroclient.hacks;

//* Import modules
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.shirodev.shiroclient.Settings;
import net.shirodev.shiroclient.Utils;

//* Flyhack class
//TODO Make base mod class.
public class flyHack {
    // * Create required variables
    public static boolean enabled = false;

    public static flyHack INSTANCE;

    static Timer fallTimer = new Timer();

    public static double count = 0d;

    // * Constructor
    public flyHack() {
        INSTANCE = this;
    }

    // * Toggle to run the mod, if enabled
    public static void toggle() {
        // * Quicker way to change it from true -> false, or false -> true
        enabled = !enabled;

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
                    if (prevCount == flyHack.count) {
                        // * Increate the previous count
                        prevCount++;
                        // * Tell the server that we are at these coordinates
                        // ? We subtract -0.4 from the y value, since we need the server to thing we are
                        // falling.
                        Utils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Settings.player.getX(),
                                Settings.player.getY() - 0.4, Settings.player.getZ(),
                                Settings.player.isOnGround()));
                    } else {
                        // * Set the counts to be equal to eachother
                        prevCount = flyHack.count;
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
}

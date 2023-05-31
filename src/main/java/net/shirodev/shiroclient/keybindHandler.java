//*  ShiroDev May 23 2023.
//*  keybindHandler.java
//*  Modified by : 

//!  Please leave credits.

package net.shirodev.shiroclient;

//* Import modules
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.shirodev.shiroclient.mods.boatfly;
import net.shirodev.shiroclient.mods.flyMod;
import net.shirodev.shiroclient.mods.speed;

//* Create the keybindHandler class
public class keybindHandler {
    // * Create the variables
    private static KeyBinding flyKeyBinding;
    private static KeyBinding tpKeyBinding;
    private static KeyBinding botflyKeyBinding;
    public static KeyBinding speedKeyBinding;
    int ticksPassed = 0;

    // * Handler constructor
    public keybindHandler() {
        // * Set the keybinds base values
        flyKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.shiroclient.fly",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.shiroclient.mods"));

        speedKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.shiroclient.speed",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                "category.shiroclient.mods"));

        tpKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.shiroclient.tp", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y, "category.shiroclient.mods"));

        botflyKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.shiroclient.boatfly",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, "category.shiroclient.mods"));

        // * Run this function at the end of all ticks.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // * Set player data
            if (Settings.player == null) {
                // ! I don't know what this warning means, or how to fix it.
                // ! If you have an idea, please tell me at puppynuff@gmail.com or
                // ! thelobsterlegion@gmail.com
                Settings.player = Settings.mc.player;
            }

            // * If the player is null, end the loop
            if (Settings.player == null)
                return;

            // * If we press the keybind toggle the mod
            if (flyKeyBinding.wasPressed()) {
                // * Toggle the mod and tell the player it toggled
                Settings.flyHackMod.toggle();
                client.player.sendMessage(Text.of("Toggled FlyMod to " + (flyMod.enabled ? "On!" : "Off!")), false);
            }

            if (tpKeyBinding.wasPressed()) {
                Settings.tpMod.toggle();
            }

            if (botflyKeyBinding.wasPressed()) {
                Settings.boatfly.toggle();
                client.player.sendMessage(Text.of("Toggled BoatFlyMod to " + (boatfly.enabled ? "On!" : "Off!")),
                        false);
            }

            if (speedKeyBinding.wasPressed()) {
                Settings.speed.toggle();
                client.player.sendMessage(Text.of("Toggled speedMod to " + (speed.enabled ? "On!" : "Off!")),
                        false);
            }

            ticksPassed += 1;
        });
    }

}

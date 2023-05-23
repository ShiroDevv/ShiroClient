//*  ShiroDev May 23 2023.
//*  keybindHandler.java
//*  Modified by : 

//!  Please leave credits.

package net.shirodev.shiroclient;

//* Import modules
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.shirodev.shiroclient.hacks.flyHack;

//* Create the keybindHandler class
public class keybindHandler {
    // * Create the variables
    private static KeyBinding flyKeyBinding;
    int ticksPassed = 0;

    // * Handler constructor
    public keybindHandler() {
        // * Set the keybinds base values
        flyKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.shiroclient.fly",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.shiroclient.hacks"));

        // * Run this function at the end of all ticks.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // * Set player data
            if (Settings.player == null) {
                // ! I don't know what this warning means, or how to fix it.
                // ! If you have an idea, please tell me at puppynuff@gmail.com or
                // ! thelobsterlegion@gmail.com
                Settings.player = MinecraftClient.getInstance().player;
            }

            // * If the player is null, end the loop
            if (Settings.player == null)
                return;

            // * If we press the keybind toggle the mod
            if (flyKeyBinding.wasPressed()) {
                // * Toggle the mod and tell the player it toggled
                // TODO Change The message to a box that stays on screen.
                flyHack.toggle();
                client.player.sendMessage(Text.of("Toggled FlyHack to " + (flyHack.enabled ? "On!" : "Off!")), false);
            }

            ticksPassed += 1;
        });
    }

}

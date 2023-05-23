//? Attempt 2!

//*  ShiroDev May 23 2023.
//*  ShiroClient.java
//*  Modified by : 

//!  Please leave credits.

package net.shirodev.shiroclient;

//* Import modules
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//* Main Class
public class ShiroClient implements ModInitializer {
    // * Create required variables
    Settings settings = new Settings();

    // * Its better practice to use this instead of the System.out.println()
    // * For modding.
    public static final Logger LOGGER = LoggerFactory.getLogger("shiroClient");

    // * When the game finishes initializing, run this
    @Override
    public void onInitialize() {
        // * Create the keybind handler
        new keybindHandler();

        // * Log that the mod initialized
        LOGGER.info("Loaded ShiroClient!");
    }
}

//*  ShiroDev May 23 2023.
//*  Settings.java
//*  Modified by : 

//!  Please leave credits.

package net.shirodev.shiroclient;

//* Importing modules
import net.minecraft.client.network.ClientPlayerEntity;
import net.shirodev.shiroclient.hacks.flyHack;

//* Creating the main settings class
public class Settings {
    // * Setting up the instance, and the player variables
    public static Settings INSTANCE;
    public static ClientPlayerEntity player;

    // * Create mod holder
    public static flyHack flyHackMod;

    // * Set the available variables.
    public Settings() {
        INSTANCE = this;
        flyHackMod = new flyHack();
    }
}

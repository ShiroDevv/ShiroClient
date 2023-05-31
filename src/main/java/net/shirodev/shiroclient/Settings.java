//*  ShiroDev May 23 2023.
//*  Settings.java
//*  Modified by : 

//!  Please leave credits.

package net.shirodev.shiroclient;

import net.minecraft.client.MinecraftClient;
//* Importing modules
import net.minecraft.client.network.ClientPlayerEntity;
import net.shirodev.shiroclient.mods.SSBKChat;
import net.shirodev.shiroclient.mods.boatfly;
import net.shirodev.shiroclient.mods.flyMod;
import net.shirodev.shiroclient.mods.speed;
import net.shirodev.shiroclient.mods.teleportMod;

//* Creating the main settings class
public class Settings {
    // * Setting up the instance, and the player variables
    public static Settings INSTANCE;
    public static ClientPlayerEntity player;
    public static MinecraftClient mc;

    // * Create mod holder
    public static flyMod flyHackMod;
    public static teleportMod tpMod;
    public static SSBKChat ssbkChat;
    public static boatfly boatfly;
    public static speed speed;

    // * Set the available variables.
    public Settings() {
        INSTANCE = this;
        flyHackMod = new flyMod();
        tpMod = new teleportMod();
        ssbkChat = new SSBKChat();
        boatfly = new boatfly();
        speed = new speed();

        mc = MinecraftClient.getInstance();
    }
}

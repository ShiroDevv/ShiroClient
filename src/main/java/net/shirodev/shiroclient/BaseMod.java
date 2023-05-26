package net.shirodev.shiroclient;

//* ShiroDev May 25 2023
//* BaseMod.java
//* Modified by : 

//! Please leave credits.

//* Base mod class.
public class BaseMod {
    public static boolean enabled = false;

    public void toggle() {
        enabled = !enabled;
    }

    public String getName() {
        return "Uh Oh - This shouldn't happen. Please submit a bug report!";
    }

    public String getToolTip() {
        return "Uh Oh - This shouldn't happen. Please submit a bug report!";
    }

    public String getHelpMessage() {
        return "Uh Oh - This shouldn't happen. Please submit a bug report!";
    }
}
//*  ShiroDev May 23 2023.
//*  Utils.java
//*  Modified by : 

//!  Please leave credits.
package net.shirodev.shiroclient;

//* Import modules
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;

//* Utils class
public class Utils {
    // * If I need to send a packet, I can use this fuction
    public static void sendPacket(Packet<?> packet) {
        // * If the networkHandler doesn't exist, don't run this and throw an error.
        // * Otherwise, send the packet.
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
            return;
        }

        throw new IllegalStateException("Cannot send packets when not in game!");
    }
}

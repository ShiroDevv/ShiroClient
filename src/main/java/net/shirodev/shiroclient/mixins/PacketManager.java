package net.shirodev.shiroclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;

@Mixin(ClientConnection.class)
public interface PacketManager {
    @Invoker("sendImmediately")
    void _sendImmediately(Packet<?> packet, PacketCallbacks callbacks);
}

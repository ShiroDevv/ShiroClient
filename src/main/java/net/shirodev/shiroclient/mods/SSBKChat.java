package net.shirodev.shiroclient.mods;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.shirodev.shiroclient.BaseMod;

public class SSBKChat extends BaseMod implements ClientCommandRegistrationCallback {

    public SSBKChat() {
        ClientCommandRegistrationCallback.EVENT.register(this);
    }

    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("SSBKChat")
                .then(ClientCommandManager.argument("Message", StringArgumentType.greedyString())
                        .executes(context -> {
                            MinecraftClient mc = MinecraftClient.getInstance();
                            String message = context.getArgument("Message", String.class);

                            mc.getNetworkHandler().sendChatCommand(String.format("msg SaIami %s", message));
                            mc.getNetworkHandler().sendChatCommand(String.format("msg Shir0Dev %s", message));
                            mc.getNetworkHandler().sendChatCommand(String.format("msg newcoolgamer200 %s", message));
                            mc.getNetworkHandler().sendChatCommand(String.format("msg ozyttv %s", message));
                            return 1;
                        }))
                .executes(context -> {
                    context.getSource().sendError(Text.of("Missing message."));
                    return 1;
                }));
    }

}

package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleTabMessage {
    private final int entityId;
    private final int tabId;

    public ToggleTabMessage(int entityId, int tabId) {
        this.entityId = entityId;
        this.tabId = tabId;
    }

    public static void encode(ToggleTabMessage message, PacketBuffer buf) {
        buf.writeInt(message.entityId);
        buf.writeInt(message.tabId);
    }

    public static ToggleTabMessage decode(PacketBuffer buf) {
        return new ToggleTabMessage(buf.readInt(), buf.readInt());
    }

    public static void handle(ToggleTabMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayerEntity sender = context.getSender();
                if (sender == null) {
                    return;
                }
                Entity entity = sender.level.getEntity(message.entityId);
                if (entity instanceof EntityMaid && ((EntityMaid) entity).isOwnedBy(sender)) {
                    ((EntityMaid) entity).openMaidGui(sender, message.tabId);
                }
            });
        }
        context.setPacketHandled(true);
    }
}

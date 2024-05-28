package baguchan.slash_illager.message;

import baguchan.slash_illager.animation.VanillaConvertedVmdAnimation;
import baguchan.slash_illager.client.ClientRenderingRegistry;
import baguchan.slash_illager.entity.IBladeAnimation;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;


public class MotionEntityBroadcastMessage  {
    public int id;
    public String combo;



    public MotionEntityBroadcastMessage(){}

    static public MotionEntityBroadcastMessage decode(FriendlyByteBuf buf) {
        MotionEntityBroadcastMessage msg = new MotionEntityBroadcastMessage();
        msg.id = buf.readInt();
        msg.combo = buf.readUtf();
        return msg;
    }

    static public void encode(MotionEntityBroadcastMessage msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.writeUtf(msg.combo);
    }

    static public void handle(MotionEntityBroadcastMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);

        if(ctx.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            return;
        }

        BiConsumer<Integer,String> handler = DistExecutor.callWhenOn(Dist.CLIENT, ()->()-> MotionEntityBroadcastMessage::setPoint);

        if(handler != null)
            ctx.get().enqueueWork(() -> {
                handler.accept(msg.id, msg.combo);
            });

    }

    @OnlyIn(Dist.CLIENT)
    static public void setPoint(int entityId, String combo){
        Entity target = Minecraft.getInstance().level.getEntity(entityId);

        if(target == null) return;

        if (!(target instanceof IBladeAnimation bladeMaster)) return;
        ComboState state = ComboStateRegistry.REGISTRY.get().getValue(ResourceLocation.tryParse(combo));
        if(state == null) return;
        ResourceLocation animation = ComboState.getRegistryKey(state);
        if(animation != null && target.level().isClientSide()) {
            VanillaConvertedVmdAnimation vmdAnimation = ClientRenderingRegistry.animation.get(animation);

            if (vmdAnimation != null) {
                bladeMaster.setCurrentAnimation(vmdAnimation.getClone());
            }
        }
    }
}
package baguchan.slash_illager.event;

import baguchan.slash_illager.SlashIllager;
import baguchan.slash_illager.message.MotionEntityBroadcastMessage;
import baguchan.slash_illager.message.NetworkManager;
import mods.flammpfeil.slashblade.event.BladeMotionEvent;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = SlashIllager.MODID)
public class ServerMotionEvent {
    @SubscribeEvent
    public static void onBladeMotion(BladeMotionEvent event){
        if(event.getEntity().level().isClientSide()) return;
        MotionEntityBroadcastMessage msg = new MotionEntityBroadcastMessage();
        msg.id = event.getEntity().getId();
        msg.combo = ComboStateRegistry.REGISTRY.get().getKey(event.getCombo()).toString();

        NetworkManager.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> event.getEntity()), msg);

    }
}

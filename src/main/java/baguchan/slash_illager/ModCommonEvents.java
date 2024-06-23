package baguchan.slash_illager;

import baguchan.slash_illager.entity.BladeMaster;
import mods.flammpfeil.slashblade.entity.EntityJudgementCut;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlashIllager.MODID)
public class ModCommonEvents {
    @SubscribeEvent
    public static void entityJoin(EntityJoinLevelEvent event) {
        if (!event.loadedFromDisk()) {
            if (event.getEntity() instanceof EntityJudgementCut entityJudgementCut && entityJudgementCut.getOwner() instanceof BladeMaster bladeMaster) {
                entityJudgementCut.setCycleHit(true);
                entityJudgementCut.setIsCritical(true);
                entityJudgementCut.setDamage(0.5F);
            }
        }
    }
}

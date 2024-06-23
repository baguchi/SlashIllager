package baguchan.slash_illager.entity.goal;

import baguchan.slash_illager.entity.BladeMaster;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

import static mods.flammpfeil.slashblade.item.ItemSlashBlade.BLADESTATE;

public class JudgementCutGoal extends Goal {
    public final BladeMaster bladeMaster;

    public JudgementCutGoal(BladeMaster bladeMaster) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.bladeMaster = bladeMaster;
    }

    public int cooldown;
    public int attackPreTime;

    @Override
    public boolean canUse() {
        if (this.cooldown <= 0) {

            if (!this.bladeMaster.isCastingSpell() && this.bladeMaster.getTarget() != null) {


                if (this.bladeMaster.distanceToSqr(this.bladeMaster.getTarget()) < 84) {
                    if (attackPreTime++ > 80) {
                        this.cooldown = 200 + this.bladeMaster.getRandom().nextInt(400);
                        return true;
                    }
                } else {
                    this.attackPreTime = 0;
                }
            }
        } else {
            if (this.cooldown > 0) {
                --this.cooldown;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        boolean result = bladeMaster.getItemInHand(InteractionHand.MAIN_HAND).getCapability(BLADESTATE).map((state) -> {
            return state.getComboSeq() == ComboStateRegistry.JUDGEMENT_CUT.getId() || state.getComboSeq() == ComboStateRegistry.JUDGEMENT_CUT_SLASH.getId() || state.getComboSeq() == ComboStateRegistry.JUDGEMENT_CUT_END.getId() || state.getComboSeq() == ComboStateRegistry.JUDGEMENT_CUT_SHEATH.getId();
        }).orElse(false);
        return result;
    }

    @Override
    public void start() {
        super.start();
        BladeMaster bladeMaster = this.bladeMaster;
        boolean result = bladeMaster.getItemInHand(InteractionHand.MAIN_HAND).getCapability(BLADESTATE).map((state) -> {
            state.updateComboSeq(bladeMaster, ComboStateRegistry.JUDGEMENT_CUT.getId());
            return true;
        }).orElse(false);
    }

    @Override
    public void tick() {
        super.tick();
        if (bladeMaster.getTarget() != null) {
            bladeMaster.lookAt(bladeMaster.getTarget(), 60F, 60F);
        }
    }
}

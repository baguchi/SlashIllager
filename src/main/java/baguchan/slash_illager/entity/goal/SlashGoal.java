package baguchan.slash_illager.entity.goal;

import baguchan.slash_illager.entity.BladeMaster;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import mods.flammpfeil.slashblade.util.InputCommand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;

import static mods.flammpfeil.slashblade.item.ItemSlashBlade.BLADESTATE;
import static mods.flammpfeil.slashblade.item.ItemSlashBlade.INPUT_STATE;

public class SlashGoal extends MeleeAttackGoal {
	public final BladeMaster bladeMaster;
	private int cooldownTime;;

	public SlashGoal(BladeMaster bladeMaster) {
        super(bladeMaster, 1.1F, true);
        this.bladeMaster = bladeMaster;
	}

	@Override
	protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
		double d0 = this.getAttackReachSqr(p_25557_);
		--this.cooldownTime;
		if (this.getTicksUntilNextAttack() < 10) {
			this.resetAttackCooldown();
			boolean result = this.mob.getItemInHand(InteractionHand.MAIN_HAND).getCapability(BLADESTATE).map((state) -> {
				this.mob.getCapability(INPUT_STATE).ifPresent((s)->s.getCommands().add(InputCommand.R_CLICK));
				if(state.isSealed() && this.cooldownTime > 0 || p_25558_ <= d0) {
					state.progressCombo(this.mob);
				}else if(!state.isSealed()){
					this.cooldownTime = 400;
					state.doChargeAction(this.mob, 10);
				}
				this.mob.getCapability(INPUT_STATE).ifPresent((s)->s.getCommands().remove(InputCommand.R_CLICK));
				return true;
			}).orElse(false);
		}

	}

	protected double getAttackReachSqr(LivingEntity p_25556_) {
		return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_25556_.getBbWidth() + 3.0F);
	}
}

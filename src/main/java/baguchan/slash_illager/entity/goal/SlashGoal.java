package baguchan.slash_illager.entity.goal;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import mods.flammpfeil.slashblade.util.InputCommand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import static mods.flammpfeil.slashblade.item.ItemSlashBlade.BLADESTATE;
import static mods.flammpfeil.slashblade.item.ItemSlashBlade.INPUT_STATE;

public class SlashGoal extends MeleeAttackGoal {
	public final PathfinderMob bladeMaster;

	public SlashGoal(PathfinderMob bladeMaster) {
        super(bladeMaster, 1.1F, true);
        this.bladeMaster = bladeMaster;
	}

	@Override
	protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
		double d0 = this.getAttackReachSqr(p_25557_);
		if (this.getTicksUntilNextAttack() < 10 && p_25558_ <= d0) {
			boolean result = this.mob.getItemInHand(InteractionHand.MAIN_HAND).getCapability(BLADESTATE).map((state) -> {
				this.mob.getCapability(INPUT_STATE).ifPresent((s)->s.getCommands().add(InputCommand.R_CLICK));
				if (!ComboStateRegistry.REGISTRY.get().containsKey(state.getComboSeq()) || !ComboStateRegistry.REGISTRY.get().containsKey(state.getComboRoot())) {
					state.setComboRoot(ComboStateRegistry.STANDBY.getId());
					state.updateComboSeq(this.mob, ComboStateRegistry.NONE.getId());
				}
				progressCombo(state, this.mob, false);
				this.resetAttackCooldown();

				this.mob.getCapability(INPUT_STATE).ifPresent((s)->s.getCommands().remove(InputCommand.R_CLICK));
				return true;
			}).orElse(false);
		}

	}

	public ResourceLocation progressCombo(ISlashBladeState comboState, LivingEntity user, boolean isVirtual) {
		ResourceLocation currentloc = comboState.resolvCurrentComboState(user);
		ComboState current = (ComboStateRegistry.REGISTRY.get()).getValue(currentloc);
		if (current != null) {
			ResourceLocation next = current.getNext(user);
			if (!next.equals(ComboStateRegistry.NONE.getId()) && next.equals(currentloc)) {
				return ComboStateRegistry.NONE.getId();
			} else {
				ComboState root = (ComboStateRegistry.REGISTRY.get()).getValue(comboState.getComboRoot());
				if (root != null) {
					ResourceLocation rootNext = root.getNext(user);
					ComboState nextCS = (ComboStateRegistry.REGISTRY.get()).getValue(next);
					ComboState rootNextCS = (ComboStateRegistry.REGISTRY.get()).getValue(rootNext);
					ResourceLocation resolved = nextCS.getPriority() <= rootNextCS.getPriority() ? next : rootNext;
					if (!isVirtual) {
						comboState.updateComboSeq(user, resolved);
					}

					return resolved;
				} else {
					return ComboStateRegistry.NONE.getId();
				}
			}
		} else {
			return ComboStateRegistry.NONE.getId();
		}
	}


	protected double getAttackReachSqr(LivingEntity p_25556_) {
		return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_25556_.getBbWidth() + 5.0F;
	}
}

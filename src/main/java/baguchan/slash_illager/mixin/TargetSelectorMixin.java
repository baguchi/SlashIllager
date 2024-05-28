package baguchan.slash_illager.mixin;

import baguchan.slash_illager.util.MobAttackManager;
import mods.flammpfeil.slashblade.util.TargetSelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TargetSelector.class, remap = false)
public class TargetSelectorMixin {
    @Redirect(method = "getTargettableEntitiesWithinAABB(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lmods/flammpfeil/slashblade/util/TargetSelector;getAreaAttackPredicate(D)Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;"))
    private static TargetingConditions injectTarget(double reach, Level world, LivingEntity attacker) {
        if (attacker instanceof Mob) {
            return MobAttackManager.getAreaAttackPredicate(reach);
        }
        return TargetSelector.getAreaAttackPredicate(reach);
    }
}

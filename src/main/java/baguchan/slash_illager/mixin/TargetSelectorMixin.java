package baguchan.slash_illager.mixin;

import baguchan.slash_illager.util.MobAttackManager;
import mods.flammpfeil.slashblade.entity.IShootable;
import mods.flammpfeil.slashblade.entity.Projectile;
import mods.flammpfeil.slashblade.util.TargetSelector;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = TargetSelector.class, remap = false)
public class TargetSelectorMixin {
    @Unique
    private static Entity slashIllager$cachedEntity;

    @Redirect(method = "getTargettableEntitiesWithinAABB(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lmods/flammpfeil/slashblade/util/TargetSelector;getAreaAttackPredicate(D)Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;"))
    private static TargetingConditions injectTarget(double reach, Level world, LivingEntity attacker, AABB aabb) {
        if (attacker instanceof Mob) {
            return MobAttackManager.getAreaAttackPredicate(reach);
        }
        return TargetSelector.getAreaAttackPredicate(reach);
    }

    @Inject(method = "getTargettableEntitiesWithinAABB(Lnet/minecraft/world/level/Level;DLnet/minecraft/world/entity/Entity;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lmods/flammpfeil/slashblade/util/TargetSelector;getAreaAttackPredicate(D)Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;"))
    private static <E extends Entity & IShootable> void injectTarget(Level world, double reach, E owner, CallbackInfoReturnable<List<Entity>> cir) {
        slashIllager$cachedEntity = owner;
    }

    @Redirect(method = "getTargettableEntitiesWithinAABB(Lnet/minecraft/world/level/Level;DLnet/minecraft/world/entity/Entity;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lmods/flammpfeil/slashblade/util/TargetSelector;getAreaAttackPredicate(D)Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;"))
    private static <E extends Entity & IShootable> TargetingConditions injectTarget(double reach) {
        if (slashIllager$cachedEntity instanceof Mob || slashIllager$cachedEntity instanceof Projectile projectile && projectile.getOwner() instanceof Mob) {
            return MobAttackManager.getAreaAttackPredicate(reach);
        }
        return TargetSelector.getAreaAttackPredicate(reach);
    }
}

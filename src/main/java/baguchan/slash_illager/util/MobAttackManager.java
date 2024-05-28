package baguchan.slash_illager.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.decoration.ArmorStand;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class MobAttackManager {
    public static final TargetingConditions areaAttack = (new TargetingConditions(true) {
        public boolean test(@Nullable LivingEntity attacker, LivingEntity target) {
            boolean isAttackable = false;
            isAttackable |= isAttackable(target.getLastHurtByMob(), attacker);
            if (!isAttackable && target instanceof Mob) {
                isAttackable |= isAttackable(((Mob) target).getTarget(), attacker);
            }

            if (isAttackable) {
                target.addTag("RevengeAttacker");
            }

            return super.test(attacker, target);
        }
    }).range(12.0).ignoreInvisibilityTesting().selector(new MobAttackablePredicate());

    static boolean isAttackable(Entity revengeTarget, Entity attacker) {
        return revengeTarget != null && attacker != null && (revengeTarget == attacker || revengeTarget.isAlliedTo(attacker));
    }

    public static TargetingConditions getAreaAttackPredicate(double reach) {
        return areaAttack.range(reach);
    }

    public static class MobAttackablePredicate implements Predicate<LivingEntity> {
        public MobAttackablePredicate() {
        }

        public boolean test(LivingEntity livingentity) {
            if (livingentity instanceof ArmorStand) {
                return ((ArmorStand) livingentity).isMarker();
            } else if (livingentity.hasPassenger((entity) -> {
                return entity.isAlliedTo(livingentity);
            })) {
                return false;
            } else if (livingentity.isCurrentlyGlowing()) {
                return true;
            } else if (livingentity.getTags().contains("RevengeAttacker")) {
                livingentity.removeTag("RevengeAttacker");
                return true;
            } else if (livingentity.getTeam() != null) {
                return true;
            } else {
                return true;
            }
        }
    }
}

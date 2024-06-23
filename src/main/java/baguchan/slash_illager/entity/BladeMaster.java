package baguchan.slash_illager.entity;

import baguchan.slash_illager.animation.VanillaConvertedVmdAnimation;
import baguchan.slash_illager.entity.goal.JudgementCutGoal;
import baguchan.slash_illager.entity.goal.SlashGoal;
import com.google.common.collect.Maps;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.entity.EntityAbstractSummonedSword;
import mods.flammpfeil.slashblade.init.SBItems;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.util.VectorHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class BladeMaster extends SpellcasterIllager implements IBladeAnimation {

    public VanillaConvertedVmdAnimation currentAnimation;

    public BladeMaster(EntityType<? extends BladeMaster> p_32105_, Level p_32106_) {
        super(p_32105_, p_32106_);
        this.xpReward = 30;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new JudgementCutGoal(this));
        this.goalSelector.addGoal(1, new SpellcasterIllager.SpellcasterCastingSpellGoal());
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new BladeSpellGoal());
        this.goalSelector.addGoal(5, new SlashGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
         this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 8.0D).add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_33306_) {
        return SoundEvents.PILLAGER_HURT;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        RandomSource randomsource = p_34088_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_34089_);
        this.populateDefaultEquipmentEnchantments(randomsource, p_34089_);
        return spawngroupdata;
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219149_, DifficultyInstance p_219150_) {
        if (this.getCurrentRaid() == null) {
            ItemStack stack = new ItemStack(SBItems.slashblade);
            Map<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.SWEEPING_EDGE, 1);
            EnchantmentHelper.setEnchantments(map, stack);

            this.setItemSlot(EquipmentSlot.MAINHAND, stack);
        }
    }


    @Override
    public void applyRaidBuffs(int p_37844_, boolean p_37845_) {
        ItemStack stack = new ItemStack(SBItems.slashblade);
        Map<Enchantment, Integer> map = Maps.newHashMap();
        if (p_37844_ > raid.getNumGroups(Difficulty.NORMAL)) {
            map.put(Enchantments.SHARPNESS, 2);
        } else if (p_37844_ > raid.getNumGroups(Difficulty.EASY)) {
            map.put(Enchantments.SHARPNESS, 1);
        }
        if (random.nextFloat() < 0.1F) {
            map.put(Enchantments.SWEEPING_EDGE, 1);
        }

        EnchantmentHelper.setEnchantments(map, stack);

        this.setItemSlot(EquipmentSlot.MAINHAND, stack);
    }

    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    public boolean isAlliedTo(Entity p_33314_) {
        if (super.isAlliedTo(p_33314_)) {
            return true;
        } else if (p_33314_ instanceof LivingEntity && ((LivingEntity) p_33314_).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && p_33314_.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide() && this.currentAnimation != null) {
            this.currentAnimation.tick();
            if (!this.currentAnimation.isActive() && this.currentAnimation.getCurrentTick() > 0) {
                this.currentAnimation = null;
            }
        }

        if(this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemSlashBlade) {
            this.getItemInHand(InteractionHand.MAIN_HAND).inventoryTick(this.level(), this, 0, true);
        }
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.ILLUSIONER_CAST_SPELL;
    }

    public void setCurrentAnimation(VanillaConvertedVmdAnimation currentAnimation) {
        this.currentAnimation = currentAnimation;
        this.currentAnimation.play();
    }

    public VanillaConvertedVmdAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return AbstractIllager.IllagerArmPose.SPELLCASTING;
        } else if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        } else {
            return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
        }
    }

    class BladeSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                LivingEntity livingentity = BladeMaster.this.getTarget();
                return livingentity != null && BladeMaster.this.hasLineOfSight(livingentity) && BladeMaster.this.distanceToSqr(livingentity) > 64F;
            }
        }

        @Override
        public void start() {
            super.start();

            BladeMaster bladeMaster = BladeMaster.this;

            Vec3 start = bladeMaster.getEyePosition(1.0f);
            Vec3 end = start.add(bladeMaster.getLookAngle().scale(40));
            HitResult result2 = bladeMaster.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, bladeMaster));
            for (int i = 0; i < 5; i++) {
                EntityAbstractSummonedSword ss = new EntityAbstractSummonedSword(SlashBlade.RegistryEvents.SummonedSword, bladeMaster.level());

                bladeMaster.level().addFreshEntity(ss);

                Vec3 pos = bladeMaster.getEyePosition(1.0f)
                        .add(VectorHelper.getVectorForRotation(0.0f, bladeMaster.getViewYRot(1.0F) + 90).scale(bladeMaster.random.nextBoolean() ? 0.25 * i + 1.5F : -0.25 * i - 1.5F));
                ss.setPos(pos.x, pos.y, pos.z);

                ss.setYRot(bladeMaster.getYRot());
                ss.setDamage(2);
                ss.setOwner(bladeMaster);
                ss.setColor(0x333333);
                ss.setRoll(bladeMaster.getRandom().nextFloat() * 360.0f);

            }
        }

        protected int getCastingTime() {
            return 60;
        }

        protected int getCastingInterval() {
            return 420;
        }

        protected void performSpellCasting() {
            BladeMaster bladeMaster = BladeMaster.this;
            LivingEntity livingentity = BladeMaster.this.getTarget();

            double d0 = livingentity.getX() - bladeMaster.getX();
            double d1 = livingentity.getY() - bladeMaster.getY();
            double d2 = livingentity.getZ() - bladeMaster.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            List<EntityAbstractSummonedSword> list = bladeMaster.level().getEntitiesOfClass(EntityAbstractSummonedSword.class, bladeMaster.getBoundingBox().inflate(6.0F));

            boolean alreadySummoned = !list.isEmpty();

            if (alreadySummoned) {
                //fire
                list.stream().forEach(e -> {
                    ((EntityAbstractSummonedSword) e).shoot(d0, d1, d2, 8.0F, 12.0F);

                });
            }
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ILLUSIONER_PREPARE_MIRROR;
        }

        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.NONE;
        }
    }
}

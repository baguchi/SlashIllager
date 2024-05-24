package baguchan.slash_illager.entity;

import baguchan.slash_illager.animation.VanillaConvertedVmdAnimation;
import baguchan.slash_illager.entity.goal.SlashGoal;
import com.google.common.collect.Maps;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.impl.animation.AnimationApplier;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
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
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import mods.flammpfeil.slashblade.init.SBItems;

import javax.annotation.Nullable;
import java.util.Map;

public class BladeMaster extends AbstractIllager{

    public VanillaConvertedVmdAnimation currentAnimation;
    public BladeMaster(EntityType<? extends AbstractIllager> p_32105_, Level p_32106_) {
        super(p_32105_, p_32106_);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
       this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new SlashGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
         this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 8.0D).add(Attributes.FOLLOW_RANGE, 30.0D);
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
            map.put(Enchantments.SHARPNESS, 1);
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
        }

        if(this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ItemSlashBlade) {
            this.getItemInHand(InteractionHand.MAIN_HAND).inventoryTick(this.level(), this, 0, false);
        }
    }

    public void setCurrentAnimation(VanillaConvertedVmdAnimation currentAnimation) {
        this.currentAnimation = currentAnimation;
        this.currentAnimation.play();
    }

    public VanillaConvertedVmdAnimation getCurrentAnimation() {
        return currentAnimation;
    }
}

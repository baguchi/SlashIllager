package baguchan.slash_illager.client.model;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.slash_illager.animation.VanillaConvertedVmdAnimation;
import baguchan.slash_illager.client.animation.HumanoidAnimations;
import baguchan.slash_illager.client.animation.IllagerAnimations;
import baguchan.slash_illager.entity.BladeMaster;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.kosmx.playerAnim.core.impl.AnimationProcessor;
import dev.kosmx.playerAnim.core.util.SetableSupplier;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import dev.kosmx.playerAnim.impl.IPlayerModel;
import dev.kosmx.playerAnim.impl.IUpperPartHelper;
import dev.kosmx.playerAnim.impl.animation.AnimationApplier;
import dev.kosmx.playerAnim.impl.animation.IBendHelper;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class BladeMasterModel<T extends BladeMaster> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {


	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public BladeMasterModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(38, 0).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.25F))
		.texOffs(60, 15).addBox(-6.5F, -1.0F, -6.5F, 13.0F, 1.0F, 13.0F, new CubeDeformation(0.25F))
		.texOffs(74, 0).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone = hat.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -1.0F, -0.5F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 18).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 20).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 20).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);

		this.animateWalk(HumanoidAnimations.IDLE, ageInTicks, 1, 0.5F, 1);
		float f = ageInTicks - (float) entity.tickCount;

		boolean flag = false;
		if (riding) {
			this.applyStatic(HumanoidAnimations.SIT);
		} else {
			this.animateWalk(HumanoidAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
			if (!flag) {
				this.animateWalk(HumanoidAnimations.WALK_SWING, limbSwing, limbSwingAmount, 2.0F, 2.5F);
			}
		}

		if (entity.isCelebrating()) {
			this.animateWalk(IllagerAnimations.CEREBRATE, ageInTicks, 1, 1, 1);

		}
		if (entity.getCurrentAnimation() != null) {
			entity.getCurrentAnimation().updatePart("head", this.head);
			entity.getCurrentAnimation().updatePart("leftArm", this.left_arm);
			entity.getCurrentAnimation().updatePart("rightArm", this.right_arm);
			entity.getCurrentAnimation().updatePart("leftLeg", this.left_leg);
			entity.getCurrentAnimation().updatePart("rightLeg", this.right_leg);
			entity.getCurrentAnimation().updatePart("torso", this.body);
		}
	}

	protected void animateBlade(AnimationState p_233382_, VanillaConvertedVmdAnimation p_233383_, float p_233384_) {
		this.animateBlade(p_233382_, p_233383_, p_233384_, 1.0F);
	}

	protected void animateBlade(AnimationState p_233386_, VanillaConvertedVmdAnimation p_233387_, float p_233388_, float p_233389_) {
		p_233386_.updateTime(p_233388_, p_233389_);
		/*p_233386_.ifStarted((p_233392_) -> {
			KeyframeAnimations.animate(this, p_233387_, p_233392_.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
		});*/
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	private ModelPart getArm(HumanoidArm p_102923_) {
		return p_102923_ == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
	}

	@Override
	public void translateToHand(HumanoidArm p_102925_, PoseStack p_102926_) {
		this.getArm(p_102925_).translateAndRotate(p_102926_);
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}
}
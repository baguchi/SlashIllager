package baguchan.slash_illager.client.render;

import baguchan.slash_illager.SlashIllager;
import baguchan.slash_illager.client.ModModelLayers;
import baguchan.slash_illager.client.model.BladeMasterModel;
import baguchan.slash_illager.entity.BladeMaster;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import mods.flammpfeil.slashblade.client.renderer.layers.LayerMainBlade;
import mods.flammpfeil.slashblade.event.client.UserPoseOverrider;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class BladeMasterRenderer<T extends BladeMaster> extends MobRenderer<T, BladeMasterModel<T>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SlashIllager.MODID, "textures/entity/illager/blade_master.png");
    public BladeMasterRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new BladeMasterModel<>(p_174304_.bakeLayer(ModModelLayers.BLADE_MASTER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, p_174304_.getModelSet(), p_174304_.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, p_174304_.getItemInHandRenderer()));
        this.addLayer(new LayerMainBlade<>(this));

    }

    @Override
    protected void setupRotations(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
        super.setupRotations(p_115317_, p_115318_, p_115319_, p_115320_, p_115321_);
        if (p_115317_.getCurrentAnimation() != null) {
            p_115317_.getCurrentAnimation().setTickDelta(p_115321_);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return TEXTURE;
    }
}

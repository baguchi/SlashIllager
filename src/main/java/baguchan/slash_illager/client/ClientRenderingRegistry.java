package baguchan.slash_illager.client;

import baguchan.slash_illager.SlashIllager;
import baguchan.slash_illager.animation.VanillaConvertedVmdAnimation;
import baguchan.slash_illager.client.model.BladeMasterModel;
import baguchan.slash_illager.client.render.BladeMasterRenderer;
import baguchan.slash_illager.registry.ModEntityRegistry;
import com.google.common.collect.Maps;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SlashIllager.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRenderingRegistry {
	public static final Map<ResourceLocation, VanillaConvertedVmdAnimation> animation = initAnimations();

	public static final ResourceLocation MotionLocation = new ResourceLocation(SlashBlade.MODID, "model/pa/player_motion.vmd");


	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntityRegistry.BLADE_MASTER.get(), BladeMasterRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.BLADE_MASTER, BladeMasterModel::createBodyLayer);
	}

	private static Map<ResourceLocation, VanillaConvertedVmdAnimation> initAnimations() {
		Map<ResourceLocation, VanillaConvertedVmdAnimation> map = Maps.newHashMap();

		//guard
		map.put(ComboStateRegistry.COMBO_A1_END2.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 21, 41, false));

		map.put(ComboStateRegistry.COMBO_A1.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1, 41, false));
		map.put(ComboStateRegistry.COMBO_A2.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 100, 151, false));
		map.put(ComboStateRegistry.COMBO_C.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 400, 488, false));
		map.put(ComboStateRegistry.COMBO_A3.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 200, 314, false));
		map.put(ComboStateRegistry.COMBO_A4.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 500, 608, false));

		map.put(ComboStateRegistry.COMBO_A4_EX.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 800, 894, false));
		map.put(ComboStateRegistry.COMBO_A5.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 900, 1061, false));

		map.put(ComboStateRegistry.COMBO_B1.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 700, 787, false));
		map.put(ComboStateRegistry.COMBO_B2.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 710, 787, false));
		map.put(ComboStateRegistry.COMBO_B3.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 710, 787, false));
		map.put(ComboStateRegistry.COMBO_B4.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 710, 787, false));
		map.put(ComboStateRegistry.COMBO_B5.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 710, 787, false));
		map.put(ComboStateRegistry.COMBO_B6.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 710, 787, false));
		map.put(ComboStateRegistry.COMBO_B7.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 710, 787, false));

		map.put(ComboStateRegistry.AERIAL_RAVE_A1.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1100, 1132, false).setBlendLegs(false));
		map.put(ComboStateRegistry.AERIAL_RAVE_A2.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1200, 1241, false).setBlendLegs(false));
		map.put(ComboStateRegistry.AERIAL_RAVE_A3.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1300, 1338, false).setBlendLegs(false));

		map.put(ComboStateRegistry.AERIAL_RAVE_B3.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1400, 1443, false).setBlendLegs(false));
		map.put(ComboStateRegistry.AERIAL_RAVE_B4.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1500, 1547, false).setBlendLegs(false));

		map.put(ComboStateRegistry.UPPERSLASH.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1600, 1693, false));
		map.put(ComboStateRegistry.UPPERSLASH_JUMP.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1700, 1717, false).setBlendLegs(false));

		map.put(ComboStateRegistry.AERIAL_CLEAVE.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1800, 1817, false).setBlendLegs(false));
		map.put(ComboStateRegistry.AERIAL_CLEAVE_LOOP.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1812, 1817, true).setBlendLegs(false));
		map.put(ComboStateRegistry.AERIAL_CLEAVE_LANDING.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1816, 1886, false));

		map.put(ComboStateRegistry.RAPID_SLASH.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 2000, 2073, false).setBlendLegs(false));
		map.put(ComboStateRegistry.RAPID_SLASH_QUICK.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 2000, 2073, false).setBlendLegs(false));
		map.put(ComboStateRegistry.RISING_STAR.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 2100, 2147, false).setBlendLegs(false));

		map.put(ComboStateRegistry.JUDGEMENT_CUT.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1900, 1963, false).setBlendLegs(false));
		map.put(ComboStateRegistry.JUDGEMENT_CUT_SLASH_AIR.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1923, 1963, false).setBlendLegs(false));
		map.put(ComboStateRegistry.JUDGEMENT_CUT_SLASH_JUST.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1923, 1963, false).setBlendLegs(false));

		map.put(ComboStateRegistry.VOID_SLASH.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 2200, 2299, false).setBlendLegs(false));

		map.put(ComboStateRegistry.SAKURA_END_LEFT.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1816, 1859, false).setBlendLegs(false));
		map.put(ComboStateRegistry.SAKURA_END_RIGHT.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 204, 314, false).setBlendLegs(false));

		map.put(ComboStateRegistry.SAKURA_END_LEFT_AIR.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1300, 1328, false).setBlendLegs(false));
		map.put(ComboStateRegistry.SAKURA_END_RIGHT_AIR.getId(), new VanillaConvertedVmdAnimation(MotionLocation, 1200, 1241, false).setBlendLegs(false));

		return map;
	}
}
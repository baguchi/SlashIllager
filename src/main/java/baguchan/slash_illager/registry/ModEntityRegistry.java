package baguchan.slash_illager.registry;

import baguchan.slash_illager.SlashIllager;
import baguchan.slash_illager.entity.BladeMaster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SlashIllager.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityRegistry {
	public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SlashIllager.MODID);

	public static final RegistryObject<EntityType<BladeMaster>> BLADE_MASTER = ENTITIES_REGISTRY.register("blade_master", () -> EntityType.Builder.of(BladeMaster::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(10).build(prefix("blade_master")));

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(BLADE_MASTER.get(), BladeMaster.createAttributes().build());
	}

	@SubscribeEvent
	public static void registerEntityAttribute(SpawnPlacementRegisterEvent event) {
		event.register(BLADE_MASTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}


	private static String prefix(String path) {
		return SlashIllager.MODID + "." + path;
	}
}
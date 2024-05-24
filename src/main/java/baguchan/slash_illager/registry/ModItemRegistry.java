package baguchan.slash_illager.registry;

import baguchan.slash_illager.SlashIllager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SlashIllager.MODID);
    public static final Supplier<Item> BLADE_MASTER_SPAWNEGG = ITEM_REGISTRY.register("blade_master_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityRegistry.BLADE_MASTER, 0x323237, 0x3C3E43, (new Item.Properties())));

}
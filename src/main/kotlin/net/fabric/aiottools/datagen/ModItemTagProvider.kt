package net.fabric.aiottools.datagen

import net.fabric.aiottools.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ModItemTagProvider(
    output: FabricDataOutput,
    completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.ItemTagProvider(output, completableFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(ItemTags.SWORDS)
            .add(ModItems.CUSTOM_TOOL)
        getOrCreateTagBuilder(ItemTags.AXES).add(
            ModItems.CUSTOM_TOOL
        )
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(
            ModItems.CUSTOM_TOOL
        )
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(
            ModItems.CUSTOM_TOOL
        )
        getOrCreateTagBuilder(ItemTags.HOES).add(
            ModItems.CUSTOM_TOOL
        )
    }
}
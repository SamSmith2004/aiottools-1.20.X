package net.fabric.aiottools.datagen

import net.fabric.aiottools.AiotTools.MOD_ID
import net.fabric.aiottools.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture


class ModItemTagProvider(
    output: FabricDataOutput,
    completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.ItemTagProvider(output, completableFuture) {
//    val aiotTag: TagKey<Item> = createTag("aiot_tool")

    private fun createTag(name: String): TagKey<Item> {
        return TagKey.of(RegistryKeys.ITEM, Identifier(MOD_ID, name))
    }

    override fun configure(arg: RegistryWrapper.WrapperLookup) {
//        getOrCreateTagBuilder(aiotTag).addOptionalTag(
//            TagKey.of(RegistryKeys.ITEM, Identifier("minecraft", "exclusive_set/mining"))
//        )

        getOrCreateTagBuilder(ItemTags.SWORDS).add(
            ModItems.CUSTOM_TOOL
        )
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
//        getOrCreateTagBuilder(aiotTag).add(
//            ModItems.CUSTOM_TOOL
//        )
    }
}
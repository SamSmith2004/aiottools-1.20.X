package net.fabric.aiottools.datagen

import net.fabric.aiottools.AiotTools.MOD_ID
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class ModBlockTagProvider(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier(MOD_ID, "aiot_effective")))
            .addOptionalTag(BlockTags.PICKAXE_MINEABLE)
            .addOptionalTag(BlockTags.AXE_MINEABLE)
            .addOptionalTag(BlockTags.SHOVEL_MINEABLE)
            .addOptionalTag(BlockTags.HOE_MINEABLE)
            .addOptionalTag(BlockTags.SWORD_EFFICIENT)
    }
}
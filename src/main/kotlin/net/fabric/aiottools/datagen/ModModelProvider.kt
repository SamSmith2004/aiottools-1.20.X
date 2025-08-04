package net.fabric.aiottools.datagen

import net.fabric.aiottools.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models

class ModModelProvider(output: FabricDataOutput): FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {}

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(ModItems.NETHERITE_AIOT, Models.HANDHELD)
        itemModelGenerator.register(ModItems.DIAMOND_AIOT, Models.HANDHELD)
        itemModelGenerator.register(ModItems.GOLD_AIOT, Models.HANDHELD)
        itemModelGenerator.register(ModItems.IRON_AIOT, Models.HANDHELD)
        itemModelGenerator.register(ModItems.OBSIDIAN_AIOT, Models.HANDHELD)
    }
}
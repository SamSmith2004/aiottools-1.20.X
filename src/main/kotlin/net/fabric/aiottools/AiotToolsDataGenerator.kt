package net.fabric.aiottools

import net.fabric.aiottools.datagen.ModBlockTagProvider
import net.fabric.aiottools.datagen.ModItemTagProvider
import net.fabric.aiottools.datagen.ModLootTableProvider
import net.fabric.aiottools.datagen.ModModelProvider
import net.fabric.aiottools.datagen.ModRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object AiotToolsDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack: FabricDataGenerator.Pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModBlockTagProvider)
		pack.addProvider(::ModItemTagProvider)
		pack.addProvider(::ModLootTableProvider)
		pack.addProvider(::ModModelProvider)
		pack.addProvider(::ModRecipeProvider)
	}
}
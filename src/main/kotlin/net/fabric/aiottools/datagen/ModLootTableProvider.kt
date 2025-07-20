package net.fabric.aiottools.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider

class ModLootTableProvider(dataOutput: FabricDataOutput): FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {}
}
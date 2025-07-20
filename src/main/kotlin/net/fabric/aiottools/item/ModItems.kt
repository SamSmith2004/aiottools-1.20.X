package net.fabric.aiottools.item

import net.fabric.aiottools.AiotTools.LOGGER
import net.fabric.aiottools.AiotTools.MOD_ID
import net.fabric.aiottools.item.tool.AiotToolItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModItems {
    val CUSTOM_TOOL: Item = registerItem(
        "custom_tool",
        AiotToolItem(ModToolMaterial.DIAMOND_AIOT, 8f, 2f,
            FabricItemSettings())
    )

    private fun registerItem(name: String, item: Item ): Item {
        return Registry.register(Registries.ITEM, Identifier(MOD_ID, name), item)
    }

    fun registerModItems() {
        LOGGER.info("Registering Mod Items for: $MOD_ID")
    }
}
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
    val NETHERITE_AIOT: Item = registerItem(
        "netherite_aiot",
        AiotToolItem(ModToolMaterial.NETHERITE_AIOT,
            FabricItemSettings())
    )
    val DIAMOND_AIOT: Item = registerItem(
        "diamond_aiot",
        AiotToolItem(ModToolMaterial.DIAMOND_AIOT,
            FabricItemSettings())
    )
    val GOLD_AIOT: Item = registerItem(
        "gold_aiot",
        AiotToolItem(ModToolMaterial.GOLD_AIOT,
            FabricItemSettings())
    )
    val IRON_AIOT: Item = registerItem(
        "iron_aiot",
        AiotToolItem(ModToolMaterial.IRON_AIOT,
            FabricItemSettings())
    )

    private fun registerItem(name: String, item: Item ): Item {
        return Registry.register(Registries.ITEM, Identifier(MOD_ID, name), item)
    }

    fun registerModItems() {
        LOGGER.info("Registering Mod Items for: $MOD_ID")
    }
}
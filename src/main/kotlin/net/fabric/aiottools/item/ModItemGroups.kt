package net.fabric.aiottools.item

import net.fabric.aiottools.AiotTools.LOGGER
import net.fabric.aiottools.AiotTools.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object ModItemGroups {
    @Suppress("unused")
    val AiotToolsGroup: ItemGroup = Registry.register(
        Registries.ITEM_GROUP,
        Identifier(MOD_ID, "aiot_tools"),
        FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.aiot_tools"))
            .icon { ItemStack(Items.NETHERITE_PICKAXE) }
            .entries { displayContext, entries ->
                entries.add(ModItems.CUSTOM_TOOL)
            }
            .build()
    )

    fun registerItemGroups() {
        LOGGER.info("Registering item groups for: $MOD_ID")
    }
}
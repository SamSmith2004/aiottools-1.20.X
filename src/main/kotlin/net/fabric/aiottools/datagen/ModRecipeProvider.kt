package net.fabric.aiottools.datagen

import net.fabric.aiottools.AiotTools.MOD_ID
import net.fabric.aiottools.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.util.Identifier
import java.util.function.Consumer

class ModRecipeProvider(output: FabricDataOutput): FabricRecipeProvider(output) {
    override fun generate(exporter: Consumer<RecipeJsonProvider>) {
        registerCraftingRecipes(exporter)
        registerSmithingRecipes(exporter)
    }

    private fun registerCraftingRecipes(exporter: Consumer<RecipeJsonProvider>) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.DIAMOND_AIOT, 1)
            .input(Items.DIAMOND_AXE)
            .input(Items.DIAMOND_PICKAXE)
            .input(Items.DIAMOND_SHOVEL)
            .input(Items.DIAMOND_SWORD)
            .input(Items.DIAMOND_HOE)
            .criterion("has_diamond_pickaxe", conditionsFromItem(Items.DIAMOND_PICKAXE))
            .criterion("has_diamond_axe", conditionsFromItem(Items.DIAMOND_AXE))
            .criterion("has_diamond_shovel", conditionsFromItem(Items.DIAMOND_SHOVEL))
            .criterion("has_diamond_sword", conditionsFromItem(Items.DIAMOND_SWORD))
            .criterion("has_diamond_hoe", conditionsFromItem(Items.DIAMOND_HOE))
            .offerTo(exporter, Identifier(MOD_ID, "diamond_aiot_from_diamond_tools"))

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GOLD_AIOT, 1)
            .input(Items.GOLDEN_AXE)
            .input(Items.GOLDEN_PICKAXE)
            .input(Items.GOLDEN_SHOVEL)
            .input(Items.GOLDEN_SWORD)
            .input(Items.GOLDEN_HOE)
            .criterion("has_golden_pickaxe", conditionsFromItem(Items.GOLDEN_PICKAXE))
            .criterion("has_golden_axe", conditionsFromItem(Items.GOLDEN_AXE))
            .criterion("has_golden_shovel", conditionsFromItem(Items.GOLDEN_SHOVEL))
            .criterion("has_golden_sword", conditionsFromItem(Items.GOLDEN_SWORD))
            .criterion("has_golden_hoe", conditionsFromItem(Items.GOLDEN_HOE))
            .offerTo(exporter, Identifier(MOD_ID, "golden_aiot_from_golden_tools"))

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_AIOT, 1)
            .input(Items.IRON_AXE)
            .input(Items.IRON_PICKAXE)
            .input(Items.IRON_SHOVEL)
            .input(Items.IRON_SWORD)
            .input(Items.IRON_HOE)
            .criterion("has_iron_pickaxe", conditionsFromItem(Items.IRON_PICKAXE))
            .criterion("has_iron_axe", conditionsFromItem(Items.IRON_AXE))
            .criterion("has_iron_shovel", conditionsFromItem(Items.IRON_SHOVEL))
            .criterion("has_iron_sword", conditionsFromItem(Items.IRON_SWORD))
            .criterion("has_iron_hoe", conditionsFromItem(Items.IRON_HOE))
            .offerTo(exporter, Identifier(MOD_ID, "iron_aiot_from_iron_tools"))
    }

    private fun registerSmithingRecipes(exporter: Consumer<RecipeJsonProvider>) {
        SmithingTransformRecipeJsonBuilder.create(
            Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
            Ingredient.ofItems(ModItems.DIAMOND_AIOT),
            Ingredient.ofItems(Items.NETHERITE_INGOT),
            RecipeCategory.TOOLS,
            ModItems.NETHERITE_AIOT
        )
            .criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT))
            .offerTo(exporter, Identifier(MOD_ID, "netherite_aiot_smithing"))
    }
}
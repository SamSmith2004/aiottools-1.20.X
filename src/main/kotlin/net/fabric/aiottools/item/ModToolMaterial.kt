package net.fabric.aiottools.item

import net.minecraft.item.Items
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient

enum class ModToolMaterial(
    private val durability: Int,
    private val miningSpeedMultiplier: Float,
    private val attackDamage: Float,
    private val miningLevel: Int,
    private val enchantability: Int,
    private val repairIngredient: Ingredient = Ingredient.EMPTY
) : ToolMaterial {
    NETHERITE_AIOT(
        durability = 2250,
        miningSpeedMultiplier = 13.0f,
        attackDamage = 15.0f,
        miningLevel = 5,
        enchantability = 30,
        repairIngredient = Ingredient.ofItems(Items.NETHERITE_INGOT),
    ),
    DIAMOND_AIOT(
        durability = 1800,
        miningSpeedMultiplier = 10.0f,
        attackDamage = 12.0f,
        miningLevel = 4,
        enchantability = 25,
        repairIngredient = Ingredient.ofItems(Items.DIAMOND),
    ),
    GOLD_AIOT(
        durability = 333,
        miningSpeedMultiplier = 20.0f,
        attackDamage = 6.0f,
        miningLevel = 3,
        enchantability = 26,
        repairIngredient = Ingredient.ofItems(Items.GOLD_INGOT),
    ),
    IRON_AIOT(
        durability = 600,
        miningSpeedMultiplier = 8.0f,
        attackDamage = 8.0f,
        miningLevel = 2,
        enchantability = 15,
        repairIngredient = Ingredient.ofItems(Items.IRON_INGOT),
    ),
    OBSIDIAN_AIOT(
        durability = 5000,
        miningSpeedMultiplier = 11.0f,
        attackDamage = 10.0f,
        miningLevel = 5,
        enchantability = 30,
        repairIngredient = Ingredient.ofItems(Items.OBSIDIAN),
    );

    override fun getDurability() = durability
    override fun getMiningSpeedMultiplier() = miningSpeedMultiplier
    override fun getAttackDamage() = attackDamage
    override fun getMiningLevel() = miningLevel
    override fun getEnchantability() = enchantability
    override fun getRepairIngredient() = repairIngredient
}